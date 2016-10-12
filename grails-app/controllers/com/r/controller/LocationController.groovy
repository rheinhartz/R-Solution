package com.r.controller

import com.r.domain.Location
import groovy.sql.Sql

class LocationController {

    def sessionFactory
    def grailsApplication
    def MainService
    def DatabaseService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show(Integer max) {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: Location.list(params), userInstanceTotal: Location.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "location_id"
        listField.add "name"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("location",listField,"")

        [list: list]
    }

    def add() {
        def locationInstance = new Location()
        locationInstance.properties = params
        
        return [ 
                    locationInstance : locationInstance
               ]
    }
    
    def edit() {
        
        def locationInstance = Location.get( params.id )
        
        if(!locationInstance) {
            flash.message = "Location not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         locationInstance : locationInstance
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "location_id"
        listField.add "name"
        listField.add "type"
        listField.add "kind"
        
        def list = mainService.listTable("location",listField,"")

        [list: list]
    }
    
    def save() {
        
        def sessionfactory = sessionFactory.currentSession
        
        def act = params.act
        def locationInstance
        
        if( act == "add" ){
            
            locationInstance = new Location( params )
       
            if(!locationInstance.hasErrors() && locationInstance.save()) {
                
                def location_id = locationInstance.id
                try{
                    def datasource = grailsApplication.mainContext.getBean('dataSource')
                    def conn = Sql.newInstance(datasource)
                    def newcol = "stock_"+location_id
                    conn.execute("call item_column_stock($newcol)")
                    conn.close()
                }catch(Exception e){
                    println e.toString()
                }
                
                try{
                            databaseService.itemTriggers()
                            databaseService.locationTriggers()

                            databaseService.physicalTriggers()
                            databaseService.physicalProcedures()

                            databaseService.withdrawalTriggers()
                            databaseService.withdrawalProcedures()

                            databaseService.receivingTriggers()
                            databaseService.receivingProcedures()

                            databaseService.adjustTriggers()
                            databaseService.adjustProcedures()

                            databaseService.transferTriggers()
                            databaseService.transferProcedures()
                }catch(Exception e){
                    println e.toString()
                }
                
                flash.message = "Location added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "Location Error"   
                render( view:'add'
                       ,model:[
                                 locationInstance   : locationInstance
                              ]
                      )
            }
        }
           
        if( act == "edit" ){
            
            locationInstance = Location.get( params.id )
            
            if(locationInstance) {
                locationInstance.properties = params

                /*
                def _toBeDeleted = locationInstance.packs.findAll {(it?.isdeleted || (it == null))}
                if (_toBeDeleted) {
                    print " - true"
                    locationInstance.packs.removeAll(_toBeDeleted)
                }else{
                    print " - false"
                }
                */
                
                
                if(!locationInstance.hasErrors() && locationInstance.save()) {
                    flash.message = "Location updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "Location Error"   
                    render(view:'edit',model:[locationInstance:locationInstance])
                }

            }
            else {
                flash.message = "Location not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
