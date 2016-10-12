package com.r.domain

class SalesItem {

    Integer sales_order_item_id
    Integer item_id
    String unit
    Double qty
    Double prc
    Double amt
    
    static belongsTo = [ sales:Sales ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        sales_order_item_id(nullable:true, blank: true)
        item_id(nullable:false, blank: false)
    }

    static mapping = {
        table 'SALES_ITEM'
        version false
        id column: "sales_item_id"
    }

    def String toString() {
        //return name
    }
}
