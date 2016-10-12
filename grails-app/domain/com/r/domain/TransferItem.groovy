package com.r.domain

class TransferItem {

    Integer item_id
    Integer location_id_fr
    Integer location_id_to
    String unit
    Double qty
    
    static belongsTo = [ transfer:Transfer ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        //item_id(nullable:false, blank: false, unique:true)
        item_id(nullable:false, blank: false, unique:false)
        location_id_fr(nullable:false, blank: false)
        location_id_to(nullable:false, blank: false)
        unit(nullable:false, blank: false)
        qty(nullable:false, blank: false)
    }

    static mapping = {
        table 'TRANSFER_ITEM'
        version false
        id column: "transfer_item_id"
    }

    def String toString() {
        //return name
    }
    
}
