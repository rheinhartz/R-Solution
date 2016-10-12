package com.r.controller

import groovy.sql.Sql
import com.r.domain.Physical

class PhysicalController {

    def sessionFactory
    def MainService
    def PhysicalService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show() {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Physical.list(params), userInstanceTotal: Physical.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "physical_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type" 
        listField.add "kind"
        
        def list = mainService.listTable("physical",listField,"")
        
        [list: list]
    }

    def add() {
        def physicalInstance = new Physical()
        physicalInstance.properties = params
        
        physicalInstance.doc_no = mainService.docNoSequence("physical")
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        return [ 
                    physicalInstance : physicalInstance
                   ,listLocation : listLocation
               ]
    }
    
    def edit() {
        
        def physicalInstance = Physical.get( params.id )
        
        physicalInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        if(!physicalInstance) {
            flash.message = "Physical not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         physicalInstance : physicalInstance
                        ,listLocation : listLocation
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "physical_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("physical",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def arr     = []
        def clist   = []
        def act = params.act
        def physicalInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        if( act == "add" ){
            
            physicalInstance = new Physical( params )
            
            def _toBeDeleted = physicalInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                physicalInstance.items.removeAll(_toBeDeleted)
            }
            
            
            clist = PhysicalService.checkDuplicate1(physicalInstance,params)
            if( clist.cond[0] ){                
                flash.message = clist.message[0]
                def x = 0
                physicalInstance.items.each{
                    it.item = params.getAt("dummy_item_"+x)
                    x++
                }
                render( view:'add'
                       ,model:[
                                 physicalInstance   : physicalInstance
                                ,listLocation : listLocation
                              ]
                      )
                return 0
            }
            
            if( !physicalInstance.hasErrors() && physicalInstance.save() ) {
                flash.message = "Physical added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Physical Error"
                render( view:'add'
                       ,model:[
                                 physicalInstance   : physicalInstance
                                ,listLocation : listLocation
                              ]
                      )
            }
        }
           
        if( act == "edit" ){
            
            physicalInstance = Physical.get( params.id )
            
            if(physicalInstance) {
                physicalInstance.properties = params
                
                def _toBeDeleted = physicalInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    physicalInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!physicalInstance.hasErrors() && physicalInstance.save()) {
                    
                    //PhysicalService.stockMethod(physicalInstance,params,sessionfactory)
                    
                    flash.message = "Physical updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Physical Error"   
                    render(view:'edit',model:[
                                                 physicalInstance:physicalInstance
                                                ,listLocation : listLocation
                                             ])
                }

            }
            else {
                flash.message = "Physical not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
