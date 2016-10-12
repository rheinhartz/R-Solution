package com.r.domain

class PurchaseOrderItem {

    Integer item_id
    String unit
    Double qty
    Double prc
    Double amt
    
    static belongsTo = [ purchaseOrder:PurchaseOrder ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        item_id(nullable:false, blank: false)
    }

    static mapping = {
        table 'PURCHASE_ORDER_ITEM'
        version false
        id column: "purchase_order_item_id"
    }

    def String toString() {
        //return name
    }
}
