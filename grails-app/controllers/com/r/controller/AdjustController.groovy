package com.r.controller

import com.r.domain.Adjust

class AdjustController {

    def sessionFactory
    def MainService
    def AdjustService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show() {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Adjust.list(params), userInstanceTotal: Adjust.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "adjust_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("adjust",listField,"")
        
        [list: list]
    }

    def add() {
        def adjustInstance = new Adjust()
        adjustInstance.properties = params
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        adjustInstance.doc_no = mainService.docNoSequence("adjust")
        /*
        def listType = []
        listType << [ idname : 'IN', fieldname : 'IN' ]
        listType << [ idname : 'OUT', fieldname : 'OUT' ]
        */
       
        return [ 
                    adjustInstance : adjustInstance
                   ,listLocation : listLocation
               ]
    }
    
    def edit() {
        
        def adjustInstance = Adjust.get( params.id )
        
        adjustInstance.items.findAll{
            def id = it?.item_id
            it.item = mainService.getField("item","name"," and a.item_id = $id")
        }
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        if(!adjustInstance) {
            flash.message = "Adjust not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         adjustInstance : adjustInstance
                        ,listLocation : listLocation
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "adjust_id"
        listField.add "doc_no"
        listField.add "location_id"
        listField.add "date"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("adjust",listField,"")

        [list: list]
    }
    
    def save() {
        def sessionfactory = sessionFactory.currentSession
        
        def arr = []
        def act = params.act
        def adjustInstance
        
        def listLocation = mainService.selectById("location","location_id","name","")
        
        if( act == "add" ){
            
            adjustInstance = new Adjust( params )
            
            def _toBeDeleted = adjustInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
            if (_toBeDeleted) {
                adjustInstance.items.removeAll(_toBeDeleted)
            }
            
            //arr = AdjustService.checkDuplicate1(adjustInstance,params)
            
            if( arr.size() > 0 ){
                flash.message = "Duplicate Location and Item: Adjust Doc No = "+arr[0].doc_no + "- Item = "+arr[0].item
                def x = 0
                adjustInstance.items.each{
                    it.item = params.getAt("dummy_item_"+x)
                    x++
                }
                render( view:'add'
                       ,model:[
                                 adjustInstance   : adjustInstance
                                ,listLocation : listLocation
                              ]
                      )
            }else{
                if( !adjustInstance.hasErrors() && adjustInstance.save() ) {

                    flash.message = "Adjust added"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Adjust Error"
                    render( view:'add'
                           ,model:[
                                     adjustInstance   : adjustInstance
                                    ,listLocation : listLocation
                                  ]
                          )
                }
            }
                
        }
           
        if( act == "edit" ){
            
            adjustInstance = Adjust.get( params.id )
            
            if(adjustInstance) {
                adjustInstance.properties = params
                
                def _toBeDeleted = adjustInstance.items.findAll {( (it?.isdeleted == "") || (it?.isdeleted == "1") || (it == null) )}
                if (_toBeDeleted) {
                    adjustInstance.items.removeAll(_toBeDeleted)
                }
                
                if(!adjustInstance.hasErrors() && adjustInstance.save()) {
                    
                    //AdjustService.stockMethod(adjustInstance,params,sessionfactory)
                    
                    flash.message = "Adjust updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Adjust Error"   
                    render(view:'edit',model:[
                                                 adjustInstance:adjustInstance
                                                ,listLocation : listLocation
                                             ])
                }

            }
            else {
                flash.message = "Adjust not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
    
}
