package com.r.controller

import com.r.domain.Contact

class ContactController {

    def MainService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show(Integer max) {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Contact.list(params), userInstanceTotal: Contact.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "contact_id"
        listField.add "name"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("contact",listField,"")

        [list: list]
    }

    def add() {
        def contactInstance = new Contact()
        contactInstance.properties = params
        
        return [ 
                    contactInstance : contactInstance
               ]
    }
    
    def edit() {
        
        def contactInstance = Contact.get( params.id )
        
        if(!contactInstance) {
            flash.message = "Contact not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         contactInstance : contactInstance
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "contact_id"
        listField.add "name"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("contact",listField,"")

        [list: list]
    }
    
    def save() {
        
        def act = params.act
        def contactInstance
        
        //println "1----------------------->"
        //println params
        
        params.iscustomer = (params.iscustomer == null ? 0 : 1)
        params.issupplier = (params.issupplier == null ? 0 : 1)
        
        //println "2----------------------->"
        //println params
        
        if( act == "add" ){
            
            contactInstance = new Contact( params )
       
            if(!contactInstance.hasErrors() && contactInstance.save()) {
                flash.message = "Contact added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Contact Error"   
                render( view:'add'
                       ,model:[
                                 contactInstance   : contactInstance
                              ]
                      )
            }
        }
           
        if( act == "edit" ){
            
            contactInstance = Contact.get( params.id )
            
            if(contactInstance) {
                contactInstance.properties = params

                /*
                def _toBeDeleted = contactInstance.packs.findAll {(it?.isdeleted || (it == null))}
                if (_toBeDeleted) {
                    print " - true"
                    contactInstance.packs.removeAll(_toBeDeleted)
                }else{
                    print " - false"
                }
                */
                
                
                if(!contactInstance.hasErrors() && contactInstance.save()) {
                    flash.message = "Contact updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Contact Error"   
                    render(view:'edit',model:[contactInstance:contactInstance])
                }

            }
            else {
                flash.message = "Contact not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
