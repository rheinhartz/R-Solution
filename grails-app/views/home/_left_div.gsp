<div id="profile" class="profile">    
    <div class="profile_pic">
    </div>
    <div class="profile_stats">
    ${session.user_fullname}
    </div>
</div>

<div id="modules_left">
    <div title="Masterfile" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_left">
            <tr onclick="fViewLeft('${createLink(uri: '/user/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Users</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/item/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Items</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/location/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Location</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/contact/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Contacts</td>
            </tr>
        </table>
    </div>
    <div title="Receivable" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_left">
            <tr onclick="fViewLeft('${createLink(uri: '/salesOrder/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Sales Order</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/sales/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Sales</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/rcreditMemo/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Credit Memo</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/rdebitMemo/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Debit Memo</td>
            </tr>
        </table>
    </div>
    <div title="Payable" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_left">
            <tr onclick="fViewLeft('${createLink(uri: '/purchaseOrder/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Purchase Order</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/purchase/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Purchase</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/pcreditMemo/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Credit Memo</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/pdebitMemo/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Debit Memo</td>
            </tr>
        </table>
    </div>
    <div title="Inventory" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_left">
            <tr onclick="fViewLeft('${createLink(uri: '/physical/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Physical</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/withdrawal/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Withdrawal</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/receiving/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Receiving</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/adjust/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Adjust</td>
            </tr>
            <tr onclick="fViewLeft('${createLink(uri: '/transfer/list')}')">
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
                <td style="width: 80%">Transfer</td>
            </tr>
        </table>
    </div>
    
</div>

<script type="text/javascript"> 
    tigra_tables('','trtbl_left', 0, 0, '#D8D8D8', '#D0D0D0', '#557DBE', '#CD5C5C');
    function fLink(str){
        window.location = str;
    }
    
    function fViewLeft(str){
        $("#center_div").load(str)
    }

    $(function() {
        $('#modules_left').accordion({
            selected: false,
            width: 200,
            height: 500,
            fit: false,
            border: true
        });
    });
</script>