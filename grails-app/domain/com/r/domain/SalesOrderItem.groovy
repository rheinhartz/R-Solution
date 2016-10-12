package com.r.domain

class SalesOrderItem {

    Integer item_id
    String unit
    Double qty
    Double prc
    Double amt
    
    static belongsTo = [ salesOrder:SalesOrder ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        item_id(nullable:false, blank: false)
    }

    static mapping = {
        table 'SALES_ORDER_ITEM'
        version false
        id column: "sales_order_item_id"
    }

    def String toString() {
        //return name
    }
}
