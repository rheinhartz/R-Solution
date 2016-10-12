package com.r.domain

class ItemPack {

    String unit
    Double packing
    Double price
    
    static belongsTo = [ item:Item ]
    
    boolean isdeleted
    static transients = [ 'isdeleted' ]

    static constraints = {
        price(nullable:true, blank: true)
    }

    static mapping = {
        table 'ITEM_PACK'
        version false
        id column: "item_pack_id"
    }

    def String toString() {
        return unit
    }
    
}
