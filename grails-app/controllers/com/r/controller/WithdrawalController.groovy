package com.r.controller

import groovy.sql.Sql
import com.r.domain.Withdrawal

class WithdrawalController {

    def sessionFactory
    def MainService
    def WithdrawalService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show() {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Withdrawal.list(params), userInstanceTotal: Withdrawal.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "withdrawal_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("withdrawal",listField,"")
        
        [list: list]
    }

    def add() {
        def withdrawalInstance = new Withdrawal()
        withdrawalInstance.properties = params
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listContact  = mainService.selectById("contact","contact_id","name","")
        
        withdrawalInstance.doc_no = mainService.docNoSequence("withdrawal")
        
        return [ 
                    withdrawalInstance : withdrawalInstance
                   ,listLocation : listLocation
                   ,listContact  : listContact
               ]
    }
    
    def edit() {
        
        def withdrawalInstance = Withdrawal.get( params.id )
        
        withdrawalInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listContact  = mainService.selectById("contact","contact_id","name","")
        
        if(!withdrawalInstance) {
            flash.message = "Withdrawal not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         withdrawalInstance : withdrawalInstance
                        ,listLocation : listLocation
                        ,listContact  : listContact
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "withdrawal_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("withdrawal",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def arr = []
        def act = params.act
        def withdrawalInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        def listContact  = mainService.selectById("contact","contact_id","name","")
        
        if( act == "add" ){
            
            withdrawalInstance = new Withdrawal( params )
            
            def _toBeDeleted = withdrawalInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                withdrawalInstance.items.removeAll(_toBeDeleted)
            }
            
            if( !withdrawalInstance.hasErrors() && withdrawalInstance.save() ) {

                flash.message = "Withdrawal added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Withdrawal Error"
                render( view:'add'
                       ,model:[
                                 withdrawalInstance   : withdrawalInstance
                                ,listLocation : listLocation
                                ,listContact  : listContact
                              ]
                      )
            }
            
                
        }
           
        if( act == "edit" ){
            
            withdrawalInstance = Withdrawal.get( params.id )
            
            if(withdrawalInstance) {
                withdrawalInstance.properties = params
                
                def _toBeDeleted = withdrawalInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    withdrawalInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!withdrawalInstance.hasErrors() && withdrawalInstance.save()) {
                    
                    //WithdrawalService.stockMethod(withdrawalInstance,params,sessionfactory)
                    
                    flash.message = "Withdrawal updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Withdrawal Error"   
                    render(view:'edit',model:[
                                                 withdrawalInstance:withdrawalInstance
                                                ,listLocation : listLocation
                                                ,listContact  : listContact
                                             ])
                }

            }
            else {
                flash.message = "Withdrawal not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
