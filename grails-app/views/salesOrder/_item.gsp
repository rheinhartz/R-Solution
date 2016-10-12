<div class="actionnav-right">
  <a href="javascript:fAddChild1()"     class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_add.png')}" />Add</a>
  <a href="javascript:fEditChild1()"    class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_edit.png')}" />Edit</a>
  <a href="javascript:fDeleteChild1()"  class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_delete.png')}" />Delete</a>
</div>

<div id="child1List"  style="margin-top: 30px;margin-left: 10px;margin-right: 10px;">
 <div style="height:250px;overflow-y:scroll;overflow-x:hidden">
  <table id="tbl_item" title="" style="width:950px;height:auto;">
    <thead>
      <tr>
        <th style="width:155px;text-align: left" data-options="sortable:true,width:155,field:'1'">Item</th>
        <th style="width:155px;text-align: left" data-options="sortable:true,width:155,field:'2'">Unit</th>
        <th style="width:155px;text-align: right" data-options="sortable:true,width:155,field:'3'">Qty</th>
        <th style="width:155px;text-align: right" data-options="sortable:true,width:155,field:'3'">Prc</th>
        <th style="width:155px;text-align: right" data-options="sortable:true,width:155,field:'3'">Amt</th>
        <th style="width:155px;text-align: right" data-options="sortable:true,width:155,field:'4'"></th>
      </tr>
    </thead>
    <tfoot style="display: none">
      <tr>
        <td style="text-align: left">
          <g:textField name='dummy_item_variable' value="item_dsc" style="width:150px"/>
          <g:textField name='variable_item.item_id' value="" style="width:50px"/>
        </td>
        <td style="text-align: left">
          <g:textField name='variable_item.unit' value=""/>
        </td>
        <td style="text-align: right">
          <g:textField name='variable_item.qty' value=""/>
        </td>
        <td style="text-align: right">
          <g:textField name='variable_item.prc' value=""/>
        </td>
        <td style="text-align: right">
          <g:textField name='variable_item.amt' value=""/>
        </td>
        <td style="text-align: right">
          <g:hiddenField name='variable_item.isdeleted' value="0"/>
          <span onclick="fDeleteChild1(variable)"><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span>
          <span onclick="fEditChild1(variable)"><img src="${resource(dir:'images/skin', file:'database_edit.png')}" /></span>
        </td> 
      </tr>
    </tfoot>
    <tbody>
    <g:each var="item" in="${sales_orderInstance.items}" status="i">
      <tr id="chidl1_tr_${i}">
        <td style="text-align: left">
          <g:textField name='dummy_item_${i}' value="${item?.item}" style="width:150px"/>
          <g:textField name='items[${i}].item_id' value="${item?.item_id}" style="width:50px"/>
        </td>
        <td style="text-align: left">
          <g:textField name='items[${i}].unit' value="${item?.unit}"/>
        </td>
        <td style="text-align: right">
          <g:textField name='items[${i}].qty' value="${item?.qty}"/>
        </td>
        <td style="text-align: right">
          <g:textField name='items[${i}].prc' value="${item?.prc}"/>
        </td>
        <td style="text-align: right">
          <g:textField name='items[${i}].amt' value="${item?.amt}"/>
        </td>
        <td style="text-align: right">
          <g:hiddenField name='items[${i}].isdeleted' value="0"/>
          <span onclick="fDeleteChild1(${i})"><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span>
          <span onclick="fEditChild1(${i})"><img src="${resource(dir:'images/skin', file:'database_edit.png')}" /></span>
        </td>
      </tr>
    </g:each>
    </tbody>

  </table>
 </div>
</div>

<div style="width:100%;text-align: center;display: none;">
  <g:textField name='selected_row' value=""/>
</div>


<!-- Pysical Item -->
<div id="dlg" class="easyui-dialog" 
     title="Pysical Item" 
     data-options="closed:true,modal:true,iconCls:'icon-save'"
     style="width:1000px;height:500px;padding:10px">
  <table>
    <tr>
      <td>Item</td>
      <td>
        <input class="easyui-combobox" id="temp_item_dsc" name="temp_item_dsc" onchange=""
               data-options="valueField:'idname'
                            ,textField:'fieldname'
                            ,required:true
                            ,onSelect:setValues
                            ,onChange:setValues
                            "
                            >
                       <!-- ,editable:false -->
        <g:hiddenField name='temp_item_id' value=""/>
      </td>
      <td>Stock</td>
      <td><input type="number" name="temp_item_stock" id="temp_item_stock" class="inputread" readonly></td>
    </tr>
    <tr>
      <td>Unit</td>
      <td>
        <input class="easyui-combobox" id="temp_item_unit" name="temp_item_unit" onchange=""
               data-options="valueField:'idname'
                            ,textField:'fieldname'
                            ,required:true
                            ,onSelect:getPacking
                            "
                            >
      </td>
      <td>Packing</td>
      <td><input type="number" name="temp_item_packing" id="temp_item_packing" class="inputread" value="0" readonly></td>
    </tr>
    <tr>
      <td>Qty</td>
      <td><input type="number" name="temp_item_qty" id="temp_item_qty" ></td>
      <td>Unit Stock</td>
      <td><input type="number" name="temp_item_unit_stock" id="temp_item_unit_stock" class="inputread" readonly></td>
    </tr>
    <tr>
      <td>Prc</td>
      <td><input type="number" name="temp_item_prc" id="temp_item_prc" ></td>
      <td></td>
      <td></td>
    </tr>
    <tr>
      <td>Amt</td>
      <td><input type="number" name="temp_item_amt" id="temp_item_amt" ></td>
      <td></td>
      <td></td>
    </tr>
  </table>
  <input type="hidden" name="temp_function" id="temp_function">
  <input type="hidden" name="temp_function_id" id="temp_function_id">
  <a href="javascript:fSaveChild1()" class="linkbutton">Save</a>
  <div id="items_list" style="display: none"></div>
  <div id="units_list" style="display: none"></div>
</div>
<!-- Pysical Item -->


<script type="text/javascript">
    var childCount1 = ${sales_orderInstance?.items.size()} + 0;
  
    $('input[type^="number"]').bind('keyup', function(){
      var n = eval( this.value )
      if( isNaN(n) ){
        //alert( (this.value).toString().length )
        this.value = (this.value).substring(0, (this.value).length-2 )
      }
    });
    
    $(document).ready(function() {
        getItem()
    });
      
    function onLoading(){
      $(window).bind("load",function(){
        //setTimeout( function(){ populateChild1() } , 100 )
      });
    }
    
    function setValues(data){      
      
      var item_id   = data.idname
      var item_name = data.fieldname
      
      if( item_id == null || item_id == "" ){
        $("#temp_item_id").val("")
        $("#temp_item_dsc").val("")
        return
      }
      
      $("#temp_item_id").val( item_id )
      $("#temp_item_dsc").val( item_name )
      
      getUnit(item_id)
      getStock(item_id)
      
    }
    
    
    function fAddChild1() {
      $( "#temp_function" ).val("add")
      $( "#temp_function_id" ).val("")
      
      $('#temp_item_dsc').combobox({ clear: true })
      $("#temp_item_id").val( "" )
      $("#temp_item_unit").combobox( 'setValue', '' )
      $("#temp_item_qty").val( "" )
      $("#temp_item_prc").val( "" )
      $("#temp_item_amt").val( "" )
      
      $("#temp_item_packing").val( "" )
      $("#temp_item_stock").val( "" )
      $("#temp_item_unit_stock").val( "" )
     
      //$("#temp_item_dsc").val( "" )
      
      $( "#dlg" ).dialog({
        closed: false
      });
    }

    function fEditChild1(id) {
      $( "#temp_function" ).val("edit")
      $( "#temp_function_id" ).val(id)
     
      var item_id = $("#items\\["+id+"\\]\\.item_id").val() //$("#temp_item_id").val()
      
      //getUnit(item_id)

      //$("#temp_item_unit").combobox( 'setValue', $("#items\\["+id+"\\]\\.unit").val() )
      //$("#temp_item_unit").combobox( 'select', 3 )
      
      $('#temp_item_dsc').combobox( 'select', $("#items\\["+id+"\\]\\.item_id").val() )
      $("#temp_item_id").val( $("#items\\["+id+"\\]\\.item_id").val() )
      $("#temp_item_qty").val( $("#items\\["+id+"\\]\\.qty").val() )
      $("#temp_item_prc").val( $("#items\\["+id+"\\]\\.prc").val() )
      $("#temp_item_amt").val( $("#items\\["+id+"\\]\\.amt").val() )
      
      getUnit(item_id)
      //$("#temp_item_unit").combobox( 'setValue', '' )
      //$('#temp_item_unit').combobox({ clear: true })
      
      var unit_url = '${createLink(uri: '/data/jsonItemUnit')}'
      $.ajax({
         async   :   true,
         url     :   unit_url,
         data    :   {
                         'item_id' : item_id
                     },
         dataType:   "json",
         success :   function(result){
                        var items = $.map(result, function(item){
                            return {
                                    idname    : item.idname,
                                    fieldname : item.fieldname
                            };
                        });
                        var unitx = $("#items\\["+id+"\\]\\.unit").val()
                        for(x=0; x<items.length; x++){
                          //alert(items[x].fieldname + "--" + unitx)
                          if( items[x].fieldname === unitx ){
                            $("#temp_item_unit").combobox( 'select', items[x].idname )
                            break;
                          }
                        }
                     },
         error   :   function(result){
                        alert("e"+result)
                     }
      });
      
      
      $( "#dlg" ).dialog({
        closed: false
      });
    }
    
    function fDeleteChild1(x) {
      $("#items\\["+x+"\\]\\.isdeleted").val( 1 )
      $("#chidl1_tr_"+x).hide()
    }

    function fSaveChild1() {
      
      var item_id = $("#temp_item_id").val()
      
      var func    = $( "#temp_function" ).val()
      var func_id = $( "#temp_function_id" ).val()
      
      if( item_id == "" ){
        alert("Item")
        return;
      }
      
      var item_id = $("#temp_item_id").val()
      var unit    = $("#temp_item_unit").combobox( 'getText' )
      var qty     = $("#temp_item_qty").val()
      var prc     = $("#temp_item_prc").val()
      var amt     = $("#temp_item_amt").val()
      
      if( func == "add" ){
        var div = $("#childList")
        var childtable1 = $("#tbl_item")

        var tfoot     = childtable1.find("tfoot:eq(0)")
        var tr_first  = tfoot.find("tr:first")

        var td = (tr_first.html()).replace(/variable_item/g, "items["+childCount1+"]")
        td = td.replace(/variable/g, childCount1)
        td = td.replace(/item_dsc/g, $("#temp_item_dsc").val() )

        var tr = $( "<tr style='' id='chidl1_tr_"+childCount1+"'>"+td+"</tr>" )

        tr.appendTo( childtable1 )
        
        //alert(tr.html())

        $("#items\\["+childCount1+"\\]\\.item_id").val( item_id )
        $("#items\\["+childCount1+"\\]\\.unit").val( unit )
        $("#items\\["+childCount1+"\\]\\.qty").val( qty )
        $("#items\\["+childCount1+"\\]\\.prc").val( prc )
        $("#items\\["+childCount1+"\\]\\.amt").val( amt )
        //$("#items\\["+childCount1+"\\]\\.unit").val( $("#temp_item_unit").val() )
        

        childCount1++;
      }
      
      if( func == "edit" ){
        $("#dummy_item_"+func_id).val( $("#temp_item_dsc").val() )
        $("#items\\["+func_id+"\\]\\.item_id").val( item_id )
        $("#items\\["+func_id+"\\]\\.unit").val( unit )
        $("#items\\["+func_id+"\\]\\.qty").val( qty )
        $("#items\\["+func_id+"\\]\\.prc").val( prc )
        $("#items\\["+func_id+"\\]\\.amt").val( amt )
      }
      
      $('#dlg').dialog('close')
      
    }

    function getItem(){
      var item_url = '${createLink(uri: '/data/jsonItem')}'
      $('#temp_item_dsc').combobox('reload', item_url )
      
      $('#items_list').html(item_url)
    }
    
    function getUnit(item_id){
      var unit_con = "?item_id="+item_id
      var unit_url = '${createLink(uri: '/data/jsonItemUnit')}' + unit_con
      $('#temp_item_unit').combobox({ clear: true })
      $('#temp_item_unit').combobox('reload', unit_url )
      
      $('#units_list').html(unit_url)
      
            
      clearTimeout(timeout)
      timer=0;
    }
    
    function getPacking(data){
      
      var item_pack_id   = data.idname
      
      var stock_url = '${createLink(uri: '/data/jsonItemPacking')}'
      
      $.ajax({
         url     :   stock_url,
         data    :   {
                         'item_pack_id' : item_pack_id
                     },
         dataType:   "json",
         success :   function(result){
                        var items = $.map(result, function(item){
                            return {
                                    idname    : item.idname,
                                    fieldname : item.fieldname
                            };
                        });
                        $('#temp_item_packing').val( items[0].fieldname )
                        
                        var stock   = $('#temp_item_stock').val()
                        var packing = $('#temp_item_packing').val()
                        
                        $('#temp_item_unit_stock').val( (stock / packing).toFixed(2) )
                     },
         error   :   function(result){
                        alert("e"+result)
                     }
      });
      
    }
    
    function getStock(item_id){
      var stock_url = '${createLink(uri: '/data/jsonItemStock')}'
      
      
      if( timer == 0 ){
        $.ajax({
           url     :   stock_url,
           data    :   {
                           'item_id' : item_id
                       },
           dataType:   "json",
           success :   function(result){
                          var items = $.map(result, function(item){
                              return {
                                      idname    : item.idname,
                                      fieldname : item.fieldname
                              };
                          });
                          
                          $('#temp_item_stock').val( items[0].fieldname )
                          
                          var packing = $('#temp_item_packing').val()
                          var stock   = $('#temp_item_stock').val()
                          $('#temp_item_unit_stock').val( (stock / packing).toFixed(2) )
                       },
           error   :   function(result){
                          alert("e"+result)
                       }
        });
        timer++
        getStock(item_id)
      }
      else{
        timeout = setTimeout( function(){
          $.ajax({
             url     :   stock_url,
             data    :   {
                             'item_id' : item_id
                         },
             dataType:   "json",
             success :   function(result){
                            var items = $.map(result, function(item){
                                return {
                                        idname    : item.idname,
                                        fieldname : item.fieldname
                                };
                            });
                            $('#temp_item_stock').val( items[0].fieldname )
                            
                            var packing = $('#temp_item_packing').val()
                            var stock   = $('#temp_item_stock').val()
                            $('#temp_item_unit_stock').val( (stock / packing).toFixed(2) )
                         },
             error   :   function(result){
                            alert("e"+result)
                         }
          });
          timer++
          getStock(item_id)
        },5000);
      }
    }
    
    onLoading()
</script>