$(document).ready ->
  ###
  Classes
  ###
  class Widget
    constructor: (@activated, @id, @name, @projectId, @updateTime, @widgetSettings) ->
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
            $('#'+this.projectId+' .'+this.name).show()
            this.setDataInView k,v for k,v of data
            this.executeAlert()
          else
            $('#'+this.projectId+' .'+this.name).hide()
            
          helper.redrawWidgetField()     
          
    setDataInView: (key, value) ->
      if value isnt null and typeof value is "object" and $('#'+this.projectId+' .'+this.name).is('.fillTableModule')
        tds = ("<td>#{val}</td>" for key, val of value).join().replace /,/g, ""
        $('#'+this.projectId+' .'+this.name+' table > tbody:last').append("<tr>#{tds}</tr>")
      else
        $('#'+this.projectId+" ."+key).text value
      
    executeAlert: ->      
      for widgetSetting in this.widgetSettings
        if widgetSetting.type is "single" and ( (Number) widgetSetting.value ) > $('#'+this.projectId+' .'+widgetSetting.name).text().replace "%",""
          $('#'+this.projectId+' .'+widgetSetting.name).css color:'red'
          #QUICK EN DIRTY -- NOT UPDATABLE PROOF
          $('#'+this.projectId+' .projectStatusBar').css 'background-color':'pink'
        else if widgetSetting.type is "bi"
          valueOperatorValue = widgetSetting.name.split(":")
          valueOne = $('#'+this.projectId+' .'+valueOperatorValue[0])
          valueTwo = $('#'+this.projectId+' .'+valueOperatorValue[2])
          operator = valueOperatorValue[1]
          
          if helper.evaluate(operator, ((Number) valueOne.text()), ((Number) valueTwo.text()))
            valueTwo.css color:'red'
            #QUICK EN DIRTY -- NOT UPDATABLE PROOF
            $('#'+this.projectId+' .projectStatusBar').css 'background-color':'pink'  
        	  
    setUpdateInterval: ->      
      @getData()
      callback = @getRefresh.bind(this)
      setInterval( callback, (this.updateTime * 1000 * 60) )
  
  class Helper
    construtor: (@name) ->
    
    redrawWidgetField: ->
      $('#projects').masonry 'reload'
    
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
  $('#projects').masonry({
    itemSelector : '.project'
  });
  
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
    widget = new Widget x.activated, x.id, x.name, x.projectId, x.updateTime, x.widgetSettings
       
  #Do extra neccecary steps
  setInterval( helper.doOneCarousel, 150000)