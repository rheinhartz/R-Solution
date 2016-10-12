package com.r.controller

import groovy.sql.Sql
import com.r.domain.Item

class ItemController {

    def grailsApplication
    def sessionFactory
    def MainService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show(Integer max) {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Item.list(params), userInstanceTotal: Item.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "item_id"
        listField.add "name"
        listField.add "brand"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("item",listField,"")

        [list: list]
    }

    def add() {
        def itemInstance = new Item()
        itemInstance.properties = params
        
        itemInstance.stock = 0
        
        return [ 
                    itemInstance : itemInstance
               ]
    }
    
    def edit() {
        
        def itemInstance = Item.get( params.id )
        
        itemInstance.stock = itemInstance.stock == null ? 0 : itemInstance.stock
        
        if(!itemInstance) {
            flash.message = "Item not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         itemInstance : itemInstance
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "item_id"
        listField.add "name"
        listField.add "brand"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("item",listField,"")

        [list: list]
    }
    
    def save() {
        
        def act = params.act
        def itemInstance
        
        if( act == "add" ){
            
            itemInstance = new Item( params )
       
            if(!itemInstance.hasErrors() && itemInstance.save()) {
                flash.message = "Item added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Item Error"   
                render( view:'add'
                       ,model:[
                                 itemInstance   : itemInstance
                              ]
                      )
            }
        }
           
        if( act == "edit" ){
            
            itemInstance = Item.get( params.id )
            
            if(itemInstance) {
                itemInstance.properties = params

                
                def _toBeDeleted = itemInstance.packs.findAll {(it?.isdeleted || (it == null))}
                if (_toBeDeleted) {
                    print " - true"
                    itemInstance.packs.removeAll(_toBeDeleted)
                }else{
                    print " - false"
                }
                
                
                if(!itemInstance.hasErrors() && itemInstance.save()) {
                    flash.message = "Item updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Item Error"   
                    render(view:'edit',model:[itemInstance:itemInstance])
                }

            }
            else {
                flash.message = "Item not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
    
}
