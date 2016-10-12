package com.r.controller

import net.sf.jasperreports.engine.data.*
import net.sf.jasperreports.engine.util.JRLoader
import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.design.JasperDesign
import net.sf.jasperreports.engine.xml.JRXmlLoader

class ReportController {

    def sessionFactory
    def MainService
    
    def index() {
    }

    def filter(){
        def listLocation = mainService.selectById("location","location_id","name","")
        
        return [ 
                listLocation:listLocation
               ]
    }
    
    def item_listing_pdf(){
        def sessionfactory = sessionFactory.currentSession
    
        String servpath = getServletContext().getRealPath("/");
        
        String jrxml    = "/report/item/listing.jrxml";
        String pdf      = "/report/item/listing.pdf";
        
        
        ///////////////////////////////////////////////////////////////////
        //Parameters
        
        def location_id = params.location_id
        
        //Parameters
        ///////////////////////////////////////////////////////////////////
        
        
        
        
                 
        HashMap<String,Object> parameters = new HashMap<String,Object>();
        def data = new ArrayList<Map<String,?>>();
            
        ///////////////////////////////////////////////////////////////////
        //Creation - Begin
        ///////////////////////////////////////////////////////////////////


        parameters.put("txtName", "");


        def sql = """
                    SELECT
                           z.module
                          ,z.module_order
                          ,z.doc_no
                          ,z.date
                          ,z.item
                          ,z.location
                          ,z.converted_qty
                          ,z.location_id
                    FROM
                    (
                            SELECT
                                 'physical' AS module
                                ,'1' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,l.location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) AS converted_qty
                            FROM physical a, physical_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.physical_id   = b.physical_id
                                AND a.location_id   = l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                            GROUP BY
                                1,2,3,4,5,6

                            UNION

                            SELECT
                                 'receiving' AS module
                                ,'2' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,l.location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) AS converted_qty
                            FROM receiving a, receiving_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.receiving_id  = b.receiving_id
                                AND a.location_id   = l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                            GROUP BY
                                1,2,3,4,5,6

                            UNION

                            SELECT
                                 'withdrawal' AS module
                                ,'2' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,l.location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) * -1 AS converted_qty
                            FROM withdrawal a, withdrawal_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.withdrawal_id  = b.withdrawal_id
                                AND a.location_id   = l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                            GROUP BY
                                1,2,3,4,5,6

                            UNION

                            SELECT
                                 'adjust_in' AS module
                                ,'2' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,l.location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) AS converted_qty
                            FROM adjust a, adjust_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.adjust_id     = b.adjust_id
                                AND a.location_id   = l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                                AND a.type          = 'IN'
                            GROUP BY
                                1,2,3,4,5,6

                            UNION

                            SELECT
                                 'adjust_out' AS module
                                ,'2' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,l.location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) * -1 AS converted_qty
                            FROM adjust a, adjust_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.adjust_id     = b.adjust_id
                                AND a.location_id   = l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                                AND a.type          = 'OUT'
                            GROUP BY
                                1,2,3,4,5,6

                            UNION

                            SELECT
                                 'transfer_in' AS module
                                ,'2' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,a.location_id_to AS location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) AS converted_qty
                            FROM transfer a, transfer_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.transfer_id   = b.transfer_id
                                AND a.location_id_to= l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                            GROUP BY
                                1,2,3,4,5,6

                            UNION

                            SELECT
                                 'transfer_out' AS module
                                ,'2' AS module_order
                                ,a.doc_no
                                ,i.name AS item
                                ,l.name AS location
                                ,a.location_id_fr AS location_id
                                ,a.date
                                ,MAX( b.qty )
                                ,MAX( i1.packing )
                                ,MAX( b.unit )
                                ,SUM(b.qty * i1.packing) * -1 AS converted_qty
                            FROM transfer a, transfer_item b, location l, item i, item_pack i1
                            WHERE 1=1
                                AND a.transfer_id   = b.transfer_id
                                AND a.location_id_fr= l.location_id
                                AND b.item_id       = i.item_id
                                AND i.item_id       = i1.item_id
                                AND b.unit          = i1.unit
                            GROUP BY
                                1,2,3,4,5,6
                     ) z
                     WHERE z.location_id = $location_id
                     ORDER BY z.item,z.module_order,z.date,z.doc_no ASC
                  """

        def query = sessionfactory.createSQLQuery(sql)
        def queryList = query.list()

        def item_temp = ""
        def runbal = 0
        def x = 0;

        queryList.each { row->
            //item, module, ,doc_no, location, date, balance, runbal
            Map field = new HashMap()

            if( item_temp != row[4] ){
                //item_temp = row[4]
                runbal = 0
                //runbal = 0//row[6]


                Map fieldx = new HashMap()
                fieldx.put("item"        ," ");
                fieldx.put("module"      ," ");
                fieldx.put("doc_no"      ," ");
                fieldx.put("location"    ," ");
                fieldx.put("date"        ," ");
                fieldx.put("balance"     ," ");
                fieldx.put("runbal"      ," ");
                data.add(fieldx)

            }else{
                item_temp = ""
                //runbal = runbal + row[6]
            }

            item_temp = row[4]
            runbal = runbal + row[6]

            field.put("item"        ,item_temp);
            field.put("module"      ,row[0]);
            field.put("doc_no"      ,row[2]);
            field.put("location"    ,row[5]);
            field.put("date"        ,row[3]);
            field.put("balance"     ,row[6]);
            field.put("runbal"      ,runbal);
            data.add(field)

            /*
            if( x > 0 ){
                if( item_temp != row[4] ){
                    item_temp = row[4]
                }else{
                    item_temp = ""
                }
            }
            */

            x++
        }


        ///////////////////////////////////////////////////////////////////
        //Creation - End
        ///////////////////////////////////////////////////////////////////


        JRMapCollectionDataSource fields = new JRMapCollectionDataSource(data);
        JasperDesign jasperdesign = JRXmlLoader.load( servpath + jrxml );
        JasperReport jasperreport = JasperCompileManager.compileReport(jasperdesign);
        JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, parameters, fields);

        response.setHeader("Content-Disposition", "inline");

        response.setContentType("application/pdf");

        JasperExportManager.exportReportToPdfStream(jasperprint, response.getOutputStream());
        JasperExportManager.exportReportToPdfFile(jasperprint, servpath + pdf );

        response.getOutputStream().close();
    }
}
