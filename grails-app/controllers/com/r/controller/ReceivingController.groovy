package com.r.controller

import com.r.domain.Receiving

class ReceivingController {

    def sessionFactory
    def MainService
    def ReceivingService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show() {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Receiving.list(params), userInstanceTotal: Receiving.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "receiving_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("receiving",listField,"")
        
        [list: list]
    }

    def add() {
        def receivingInstance = new Receiving()
        receivingInstance.properties = params
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listContact  = mainService.selectById("contact","contact_id","name","")
        
        receivingInstance.doc_no = mainService.docNoSequence("receiving")
        
        return [ 
                    receivingInstance : receivingInstance
                   ,listLocation : listLocation
                   ,listContact  : listContact
               ]
    }
    
    def edit() {
        
        def receivingInstance = Receiving.get( params.id )
        
        receivingInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listContact  = mainService.selectById("contact","contact_id","name","")
        
        if(!receivingInstance) {
            flash.message = "Receiving not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         receivingInstance : receivingInstance
                        ,listLocation : listLocation
                        ,listContact  : listContact
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "receiving_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("receiving",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def arr = []
        def act = params.act
        def receivingInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listContact  = mainService.selectById("contact","contact_id","name","")
        
        if( act == "add" ){
            
            receivingInstance = new Receiving( params )
            
            def _toBeDeleted = receivingInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                receivingInstance.items.removeAll(_toBeDeleted)
            }
            
            if( !receivingInstance.hasErrors() && receivingInstance.save() ) {

                flash.message = "Receiving added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Receiving Error"
                render( view:'add'
                       ,model:[
                                 receivingInstance   : receivingInstance
                                ,listLocation : listLocation
                                ,listContact  : listContact
                              ]
                      )
            }
            
                
        }
           
        if( act == "edit" ){
            
            receivingInstance = Receiving.get( params.id )
            
            if(receivingInstance) {
                receivingInstance.properties = params
                
                def _toBeDeleted = receivingInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    receivingInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!receivingInstance.hasErrors() && receivingInstance.save()) {
                    
                    //ReceivingService.stockMethod(receivingInstance,params,sessionfactory)
                    
                    flash.message = "Receiving updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Receiving Error"   
                    render(view:'edit',model:[
                                                 receivingInstance:receivingInstance
                                                ,listLocation : listLocation
                                                ,listContact  : listContact
                                             ])
                }

            }
            else {
                flash.message = "Receiving not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
