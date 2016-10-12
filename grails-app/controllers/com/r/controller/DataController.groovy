package com.r.controller

import grails.converters.JSON
import grails.converters.XML

class DataController {

    def sessionFactory
    def mainService
    
    def index() { }
    
    def jsonData(){
        
        def table       = params.tablename
        def id          = params.id
        def field       = params.field
        def condition   = params.condition
        
        def arr = mainService.selectById(table, id, field ,condition)
       
        try{
            
        }catch(Exception e){
            println e.toString()
        }
        
        render arr as JSON        
    }
    
    def jsonItem(){
        
        def arr = mainService.selectById("item", "item_id", "name" ,"")
       
        try{
            
        }catch(Exception e){
            println e.toString()
        }
        
        render arr as JSON
    }
    
    def jsonItemUnit(){
        
        def condition  = ""
        def item_id   = params.item_id
        
        if( item_id ){
            condition = " AND a.item_id = $item_id"
        }
        
        def arr = mainService.selectById("item_pack", "item_pack_id", "unit" ,condition)
       
        try{
            
        }catch(Exception e){
            println e.toString()
        }
        
        render arr as JSON
    }
    
    def jsonItemPacking(){
        
        def condition  = ""
        def item_pack_id   = params.item_pack_id
        
        if( item_pack_id ){
            condition = " AND a.item_pack_id = $item_pack_id"
        }
        
        def arr = mainService.selectById("item_pack", "item_pack_id", "packing" ,condition)
       
        try{
            
        }catch(Exception e){
            println e.toString()
        }
        
        render arr as JSON
    }
    
    def jsonItemStock(){
        
        def condition  = ""
        def item_id     = params.item_id
        def location_id = params.location_id
        
        def stocknum = "stock"
        
        if( item_id ){
            condition = " AND a.item_id = $item_id"
        }
        
        if( location_id ){
            stocknum = "stock_"+location_id
        }
        
        def arr = mainService.selectById("item", "item_id", stocknum ,condition)
       
        try{
            
        }catch(Exception e){
            println e.toString()
        }
        
        render arr as JSON
    }
    
}
