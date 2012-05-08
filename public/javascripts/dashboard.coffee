$(document).ready ->
  ###
  Classes
  ###
  class Widget
    constructor: (@name) ->

    getSettings: ->
      alert this.settingsUrl

    getData: ->
      $.ajax this.dataUrl,
        type: 'GET'
        dataType: 'html'
        success: (data) ->
          console.log data
  
  class Helper
    construtor: (@name) ->
    
    doOneCarousel: ->
      $('#projects .clearBoth').before $('.project::eq(0), .project::eq(1)')
 
  ###
  Init
  ###
  
  #Add widgets
  $('.project').each ->
    widget = new Widget "Voortgangsrapportage Fuo"
    widget.dataUrl = jsRoutes.controllers.Dashboard.getProgressReport($(this).attr "id").url
    widget.settingsUrl = jsRoutes.controllers.Dashboard.getProgressReport($(this).attr "id").url
    widget.getData()
    
  #Add helper class
  helper = new Helper "HelperFunctions"
  
  #Do extra neccecary steps
  setInterval( helper.doOneCarousel, 15000)