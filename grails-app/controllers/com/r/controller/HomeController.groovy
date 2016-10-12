package com.r.controller

class HomeController {

    def MainService
    def DatabaseService
    
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "show", params: params)
    }

    def show(Integer max) {

        //session['username'] = "Eric"
        //params.max = Math.min(max ?: 10, 100)
        //[list: list]
    }
}
