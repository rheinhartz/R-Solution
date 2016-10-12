package com.r.domain

class PurchaseItem {

    Integer purchase_order_item_id
    Integer item_id
    String unit
    Double qty
    Double prc
    Double amt
    
    static belongsTo = [ purchase:Purchase ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        purchase_order_item_id(nullable:true, blank: true)
        item_id(nullable:false, blank: false)
    }

    static mapping = {
        table 'PURCHASE_ITEM'
        version false
        id column: "purchase_item_id"
    }

    def String toString() {
        //return name
    }
}
