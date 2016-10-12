package com.r.domain

class Population {

    String region
    String city
    String barangay
    Integer population
    
    static constraints = {
        region(nullable:true, blank: true)
        city(nullable:true, blank: true)
        barangay(nullable:true, blank: true)
        population(nullable:true, blank: true)
    }

    static mapping = {
        table 'POPULATION'
        version false
        id column: "population_id"
    }
}
