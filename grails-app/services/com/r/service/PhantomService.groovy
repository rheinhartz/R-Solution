package com.r.service

class PhantomService {

    static scope = "session"

    def grailsApplication
    def phantomJsPort
    def phantomJsServer
    
    def getPhantomJsUrl() {
        //phantomJsPort = grailsApplication.config.eappro.phantomjs.port
        phantomJsServer = grailsApplication.config.rsolution.phantomjs.host
        """http://${this.phantomJsServer}:${this.phantomJsPort}/?url="""
    }
}
