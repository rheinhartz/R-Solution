package com.r.domain

class ReceivingItem {

    Integer item_id
    Integer location_id
    String unit
    Double qty
    
    static belongsTo = [ receiving:Receiving ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        //item_id(nullable:false, blank: false, unique:true)
        item_id(nullable:false, blank: false, unique:false)
        location_id(nullable:false, blank: false)
        unit(nullable:false, blank: false)
        qty(nullable:false, blank: false)
    }

    static mapping = {
        table 'RECEIVING_ITEM'
        version false
        id column: "receiving_item_id"
    }

    def String toString() {
        //return name
    }
    
}
