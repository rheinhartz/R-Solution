package com.r.controller

import com.r.domain.Transfer

class TransferController {

    def sessionFactory
    def MainService
    //def TransferService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show() {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Transfer.list(params), userInstanceTotal: Transfer.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "transfer_id"
        listField.add "doc_no"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("transfer",listField,"")
        
        [list: list]
    }

    def add() {
        def transferInstance = new Transfer()
        transferInstance.properties = params
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        transferInstance.doc_no = mainService.docNoSequence("transfer")
       
        return [ 
                    transferInstance : transferInstance
                   ,listLocation : listLocation
               ]
    }
    
    def edit() {
        
        def transferInstance = Transfer.get( params.id )
        
        transferInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        if(!transferInstance) {
            flash.message = "Transfer not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         transferInstance : transferInstance
                        ,listLocation : listLocation
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "transfer_id"
        listField.add "doc_no"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("transfer",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def arr = []
        def act = params.act
        def transferInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        if( act == "add" ){
            
            transferInstance = new Transfer( params )
            
            def _toBeDeleted = transferInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                transferInstance.items.removeAll(_toBeDeleted)
            }
            
            
            if( !transferInstance.hasErrors() && transferInstance.save() ) {

                flash.message = "Transfer added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Transfer Error"
                render( view:'add'
                       ,model:[
                                 transferInstance   : transferInstance
                                ,listLocation : listLocation
                              ]
                      )
            }
                
        }
           
        if( act == "edit" ){
            
            transferInstance = Transfer.get( params.id )
            
            if(transferInstance) {
                transferInstance.properties = params
                
                def _toBeDeleted = transferInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    transferInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!transferInstance.hasErrors() && transferInstance.save()) {
                    
                    //TransferService.stockMethod(transferInstance,params,sessionfactory)
                    
                    flash.message = "Transfer updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Transfer Error"   
                    render(view:'edit',model:[
                                                 transferInstance:transferInstance
                                                ,listLocation : listLocation
                                             ])
                }

            }
            else {
                flash.message = "Transfer not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
