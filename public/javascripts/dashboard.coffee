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
 
  ###
  Init
  ###
  $('.project').each ->
    widget = new Widget "Voortgangsrapportage Fuo"
    widget.dataUrl = jsRoutes.controllers.Dashboard.getProgressReport($(this).attr "id").url
    widget.settingsUrl = jsRoutes.controllers.Dashboard.getProgressReport($(this).attr "id").url
    widget.getData()