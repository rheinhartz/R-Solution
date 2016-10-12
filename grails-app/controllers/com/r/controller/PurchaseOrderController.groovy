package com.r.controller

import groovy.sql.Sql
import com.r.domain.PurchaseOrder

class PurchaseOrderController {

    def MainService
    def sessionFactory
    
    def index() {
        def sessionfactory = sessionFactory.currentSession
        
        def sql1 = """
                    CREATE TRIGGER `purchase_order_stock_add` AFTER INSERT
                    ON `purchase_order_item`
                    FOR EACH ROW BEGIN
                            UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                    END
                   """
        def sql2 = """
                    CREATE TRIGGER `purchase_order_stock_edit` AFTER UPDATE
                    ON `purchase_order_item`
                    FOR EACH ROW BEGIN
                        UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                            + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                        WHERE a.item_id = NEW.item_id;
                    END
                   """
        def sql3 = """
                    CREATE TRIGGER `purchase_order_stock_delete` BEFORE DELETE
                    ON `purchase_order_item`
                    FOR EACH ROW BEGIN
                        UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                        WHERE a.item_id = OLD.item_id;
                    END
                   """
       
        //println sql 

        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            //conn.execute(sql1)
            //conn.execute(sql2)
            //conn.execute(sql3)
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
        
        redirect(action: "show", params: params)
    }

    def show(Integer max) {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: PurchaseOrder.list(params), userInstanceTotal: PurchaseOrder.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "purchase_order_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("purchase_order",listField,"")
        
        [list: list]
    }

    def add() {
        def purchase_orderInstance = new PurchaseOrder()
        purchase_orderInstance.properties = params
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listSupplier = mainService.selectById("contact","contact_id","name"," AND a.type = 'SUPPLIER' ")
        
        return [ 
                    purchase_orderInstance : purchase_orderInstance
                   ,listLocation : listLocation
                   ,listSupplier : listSupplier
               ]
    }
    
    def edit() {
        
        def purchase_orderInstance = PurchaseOrder.get( params.id )
        
        purchase_orderInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listSupplier = mainService.selectById("contact","contact_id","name"," AND a.type = 'SUPPLIER' ")
        
        if(!purchase_orderInstance) {
            flash.message = "PurchaseOrder not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         purchase_orderInstance : purchase_orderInstance
                        ,listLocation : listLocation
                        ,listSupplier : listSupplier
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "purchase_order_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("purchase_order",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def act = params.act
        def purchase_orderInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listSupplier = mainService.selectById("contact","contact_id","name"," AND a.type = 'SUPPLIER' ")
        
        if( act == "add" ){
            
            purchase_orderInstance = new PurchaseOrder( params )
            
            def _toBeDeleted = purchase_orderInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                purchase_orderInstance.items.removeAll(_toBeDeleted)
            }
            
            def qsize = 0
            def qarr = []
            def locationid = purchase_orderInstance.location_id
            purchase_orderInstance.items.findAll{
                def itemid = it?.item_id
                
                def sql = """
                            SELECT a.doc_no,i.name
                            FROM purchase_order a, purchase_order_item b, item i
                            WHERE a.purchase_order_id = b.purchase_order_id
                            AND a.location_id = $locationid
                            AND b.item_id = $itemid
                            AND i.item_id = b.item_id
                            GROUP BY a.doc_no,i.name
                          """
                
                def query = sessionfactory.createSQLQuery(sql.toString())
                def queryList = query.list()
                qsize = queryList.size()
                
                if( qsize > 0 ){
                    queryList.each{row->
                        qarr << [ doc_no : row[0], item : row[1] ]
                    }
                }
                //println "---------------------------------------"
                //println queryList.size() + "->"
                //println sql
                //println "---------------------------------------"
            }

            if( qarr.size() > 0 ){
                flash.message = "Duplicate Location and Item: PurchaseOrder Doc No = "+qarr[0].doc_no + "- Item = "+qarr[0].item
                def x = 0
                purchase_orderInstance.items.each{
                    it.item = params.getAt("dummy_item_"+x)
                    x++
                }
                render( view:'add'
                       ,model:[
                                 purchase_orderInstance   : purchase_orderInstance
                                ,listLocation : listLocation
                                ,listSupplier : listSupplier
                              ]
                      )
            }else{

                if( !purchase_orderInstance.hasErrors() && purchase_orderInstance.save() ) {
                    flash.message = "PurchaseOrder added"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "PurchaseOrder Error"
                    render( view:'add'
                           ,model:[
                                     purchase_orderInstance   : purchase_orderInstance
                                    ,listLocation : listLocation
                                    ,listSupplier : listSupplier
                                  ]
                          )
                }
                
            }
        }
           
        if( act == "edit" ){
            
            purchase_orderInstance = PurchaseOrder.get( params.id )
            
            if(purchase_orderInstance) {
                purchase_orderInstance.properties = params

                def _toBeDeleted = purchase_orderInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    purchase_orderInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!purchase_orderInstance.hasErrors() && purchase_orderInstance.save()) {
                    flash.message = "PurchaseOrder updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "PurchaseOrder Error"   
                    render(view:'edit',model:[
                                                 purchase_orderInstance:purchase_orderInstance
                                                ,listLocation : listLocation
                                                ,listSupplier : listSupplier
                                             ])
                }

            }
            else {
                flash.message = "PurchaseOrder not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
