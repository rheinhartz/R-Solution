package com.r.controller

import groovy.sql.Sql
import com.r.domain.SalesOrder

class SalesOrderController {

    def MainService
    def sessionFactory
    
    def index() {
        def sessionfactory = sessionFactory.currentSession
        
        def sql1 = """
                    CREATE TRIGGER `sales_order_stock_add` AFTER INSERT
                    ON `sales_order_item`
                    FOR EACH ROW BEGIN
                            UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                    END
                   """
        def sql2 = """
                    CREATE TRIGGER `sales_order_stock_edit` AFTER UPDATE
                    ON `sales_order_item`
                    FOR EACH ROW BEGIN
                        UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                            + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                        WHERE a.item_id = NEW.item_id;
                    END
                   """
        def sql3 = """
                    CREATE TRIGGER `sales_order_stock_delete` BEFORE DELETE
                    ON `sales_order_item`
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
        //[userInstanceList: SalesOrder.list(params), userInstanceTotal: SalesOrder.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "sales_order_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("sales_order",listField,"")
        
        [list: list]
    }

    def add() {
        def sales_orderInstance = new SalesOrder()
        sales_orderInstance.properties = params
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listCustomer = mainService.selectById("contact","contact_id","name"," AND a.type = 'CUSTOMER' ")
        
        return [ 
                    sales_orderInstance : sales_orderInstance
                   ,listLocation : listLocation
                   ,listCustomer : listCustomer
               ]
    }
    
    def edit() {
        
        def sales_orderInstance = SalesOrder.get( params.id )
        
        sales_orderInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listCustomer = mainService.selectById("contact","contact_id","name"," AND a.type = 'CUSTOMER' ")
        
        if(!sales_orderInstance) {
            flash.message = "SalesOrder not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         sales_orderInstance : sales_orderInstance
                        ,listLocation : listLocation
                        ,listCustomer : listCustomer
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "sales_order_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("sales_order",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def act = params.act
        def sales_orderInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listCustomer = mainService.selectById("contact","contact_id","name"," AND a.type = 'CUSTOMER' ")
        
        if( act == "add" ){
            
            sales_orderInstance = new SalesOrder( params )
            
            def _toBeDeleted = sales_orderInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                sales_orderInstance.items.removeAll(_toBeDeleted)
            }
            
            def qsize = 0
            def qarr = []
            def locationid = sales_orderInstance.location_id
            sales_orderInstance.items.findAll{
                def itemid = it?.item_id
                
                def sql = """
                            SELECT a.doc_no,i.name
                            FROM sales_order a, sales_order_item b, item i
                            WHERE a.sales_order_id = b.sales_order_id
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
                flash.message = "Duplicate Location and Item: SalesOrder Doc No = "+qarr[0].doc_no + "- Item = "+qarr[0].item
                def x = 0
                sales_orderInstance.items.each{
                    it.item = params.getAt("dummy_item_"+x)
                    x++
                }
                render( view:'add'
                       ,model:[
                                 sales_orderInstance   : sales_orderInstance
                                ,listLocation : listLocation
                                ,listCustomer : listCustomer
                              ]
                      )
            }else{

                if( !sales_orderInstance.hasErrors() && sales_orderInstance.save() ) {
                    flash.message = "SalesOrder added"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "SalesOrder Error"
                    render( view:'add'
                           ,model:[
                                     sales_orderInstance   : sales_orderInstance
                                    ,listLocation : listLocation
                                    ,listCustomer : listCustomer
                                  ]
                          )
                }
                
            }
        }
           
        if( act == "edit" ){
            
            sales_orderInstance = SalesOrder.get( params.id )
            
            if(sales_orderInstance) {
                sales_orderInstance.properties = params

                def _toBeDeleted = sales_orderInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    sales_orderInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!sales_orderInstance.hasErrors() && sales_orderInstance.save()) {
                    flash.message = "SalesOrder updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "SalesOrder Error"   
                    render(view:'edit',model:[
                                                 sales_orderInstance:sales_orderInstance
                                                ,listLocation : listLocation
                                                ,listCustomer : listCustomer
                                             ])
                }

            }
            else {
                flash.message = "SalesOrder not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
