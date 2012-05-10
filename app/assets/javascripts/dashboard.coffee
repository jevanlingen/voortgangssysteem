$(document).ready ->
  ###
  Classes
  ###
  class Widget
    constructor: (@activated, @id, @name, @project_id, @updateTime, @widgetSettings) ->
      this.dataUrl = eval "jsRoutes.controllers.Widget.get#{this.name}(this.id).url"
      this.setUpdateInterval()
      
    getRefresh: ->
      this.updateSettings()
      this.getData()
      

    updateSettings: ->
      #console.log this.settingsUrl

    getData: ->    
      $.ajax this.dataUrl,
        type: 'GET'
        dataType: 'json'
        success: (data) =>
          this.data = data
          if this.activated
            this.setDataInView k,v for k,v of data
            this.executeAlert()
          
    setDataInView: (key, value) ->
      if value isnt null and typeof value is "object" and $('#'+this.project_id+' .'+this.name).is('.fillTableModule')
        tds = ("<td>#{val}</td>" for key, val of value).join().replace /,/g, ""
        $('#'+this.project_id+' .'+this.name+' table > tbody:last').append("<tr>#{tds}</tr>")
      else
        $('#'+this.project_id+" ."+key).text value
      
    executeAlert: ->
      for widgetSetting in this.widgetSettings
        if widgetSetting.type is "bi"
        	valueOperatorValue = widgetSetting.name.split(":")
        	valueOne = $('#'+this.project_id+' .'+valueOperatorValue[0])
        	valueTwo = $('#'+this.project_id+' .'+valueOperatorValue[2])
        	operator = valueOperatorValue[1]
        	
        	if helper.evaluate(operator, ((Number) valueOne.text()), ((Number) valueTwo.text()))
        	  valueTwo.css color:'red'
        	  
    setUpdateInterval: ->      
      @getData()
      callback = @getRefresh.bind(this)
      setInterval( callback, (this.updateTime * 1000 * 60) )
  
  class Helper
    construtor: (@name) ->
    
    doOneCarousel: ->
      $('#projects .clearBoth').before $('.project::eq(0), .project::eq(1)')
    
    evaluate: (operator, param1, param2) ->
      switch operator
        when "+" then param1 + param2
        when "-" then param1 - param2
        when "*" then param1 * param2
        when "/" then param1 / param2
        when "<" then param1 < param2
        when ">" then param1 > param2
 
  ###
  Init
  ### 
  #Add helper class
  helper = new Helper "HelperFunctions"
  
  #Add widgets
  $('.project').each ->
    $.ajax jsRoutes.controllers.Widget.getWidgets($(this).attr "id").url,
        type: 'GET'
        dataType: 'json'
        success: (data) ->
          createWidget object for object in data
  
  createWidget = (x) ->
    widget = new Widget x.activated, x.id, x.name, x.project_id, x.updateTime, x.widgetSettings
       
  #Do extra neccecary steps
  setInterval( helper.doOneCarousel, 150000)