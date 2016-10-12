package com.r.domain

class WithdrawalItem {

    Integer item_id
    Integer location_id
    String unit
    Double qty
    
    static belongsTo = [ withdrawal:Withdrawal ]
    
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
        table 'WITHDRAWAL_ITEM'
        version false
        id column: "withdrawal_item_id"
    }

    def String toString() {
        //return name
    }
    
}
