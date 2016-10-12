package com.r.domain

class AdjustItem {

    Integer item_id
    Integer location_id
    //String adj_type
    String unit
    String remark
    Double qty
    
    static belongsTo = [ adjust:Adjust ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        //item_id(nullable:false, blank: false, unique:true)
        item_id(nullable:false, blank: false, unique:false)
        location_id(nullable:false, blank: false)
        unit(nullable:false, blank: false)
        qty(nullable:false, blank: false)
        remark(nullable:true, blank: true)
    }

    static mapping = {
        table 'ADJUST_ITEM'
        version false
        id column: "adjust_item_id"
    }

    def String toString() {
        //return name
    }
    
}
