package com.r.domain

class Location {

    String name
    String code
    String type
    String kind
    String region
    String city
    String munincipal
    String barangay
    String street
    String no

    Integer isdeleted    

    static constraints = {
        name(blank: true)
        type(blank: true)
        kind(blank: true)
        region(nullable:true, blank: true)
        city(nullable:true, blank: true)
        munincipal(nullable:true, blank: true)
        barangay(nullable:true, blank: true)
        street(nullable:true, blank: true)
        no(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'LOCATION'
        version false
        id column: "location_id"
        
        isdeleted defaultValue: 0
    }
}
