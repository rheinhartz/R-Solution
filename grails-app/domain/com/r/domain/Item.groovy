package com.r.domain

class Item {

    String name
    String code
    String kind
    String type
    String brand
    Double stock
    Integer isdeleted
    
    List packs = new ArrayList()
    
    static hasMany = [packs: ItemPack]

    static constraints = {
        name(nullable:false, blank: false, unique:true)
        stock(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'ITEM'
        version false
        id column: "item_id"
        
        stock column: 'stock', defaultValue: '0'
        
        isdeleted column: 'isdeleted', defaultValue: 0
        
        packs cascade: "all-delete-orphan"
    }
}