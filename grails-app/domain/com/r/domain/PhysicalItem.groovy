package com.r.domain

class PhysicalItem {

    Integer item_id
    Integer location_id
    String unit
    Double qty
    
    static belongsTo = [ physical:Physical ]
    
    String item
    String isdeleted
    static transients = [ 'isdeleted','item' ]

    static constraints = {
        //item_id(nullable:false, blank: false, unique:true)
        item_id(nullable:false, blank: false, unique:false
                /*
                validator: {
                    
                    Physical.findAll{
                        def lid = it.location_id
                        def sql = """
                                SELECT 1 FROM physical a, physical_item b
                                WHERE a.physical_id = b.physical_id
                                AND a.location_id = ?
                                AND b.item_id = ?
                              """
                        PhysicalItem.findAll{
                            if( it.executeQuery(sql,lid,it.item_id) > 0 ){
                                return false
                            }
                        }

                        return true
                    }
                    return true
                    
                }
                */
               )
        location_id(nullable:false, blank: false)
        unit(nullable:false, blank: false)
        qty(nullable:false, blank: false)
    }

    static mapping = {
        table 'PHYSICAL_ITEM'
        version false
        id column: "physical_item_id"
    }

    def String toString() {
        //return name
    }
    
}
