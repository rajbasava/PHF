/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.web.controller;

import com.yvphfk.common.ImportObjectMeta;
import com.yvphfk.model.ImportFile;
import com.yvphfk.model.Login;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ImportFileController extends CommonController
{
    @Autowired
    private ImportService importService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/importRegistrations")
    public String importRegistrations (ImportFile importFile,
                                       Map<String, Object> map,
                                       HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        MultipartFile file = importFile.getFile();
        importFile.setMeta(ImportObjectMeta.Registrations);
        if (file != null) {
            // todo validations for type of the file
            importService.processImportFile(importFile, login);
        }
        map.put("results", file.getOriginalFilename());
        return "importResults";
    }

    @RequestMapping(value = "/importRowMeta")
    public String importRowMeta (ImportFile importFile,
                                 Map<String, Object> map,
                                 HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        MultipartFile file = importFile.getFile();
        importFile.setMeta(ImportObjectMeta.RowMeta);
        if (file != null) {
            // todo validations for type of the file
            importService.processImportFile(importFile, login);
        }
        map.put("results", file.getOriginalFilename());
        return "importResults";
    }

    @RequestMapping(value = "/importCustomSeats")
    public String importCustomSeats (ImportFile importFile,
                                     Map<String, Object> map,
                                     HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        MultipartFile file = importFile.getFile();
        importFile.setMeta(ImportObjectMeta.CustomSeats);
        if (file != null) {
            // todo validations for type of the file
            importService.processImportFile(importFile, login);
        }
        map.put("results", file.getOriginalFilename());
        return "importResults";
    }

    @RequestMapping(value = "/importUpdateRegistration")
    public String importUpdateRegistration (ImportFile importFile,
                                            Map<String, Object> map,
                                            HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        MultipartFile file = importFile.getFile();
        importFile.setMeta(ImportObjectMeta.RegistrationsUpdate);
        if (file != null) {
            // todo validations for type of the file
            importService.processImportFile(importFile, login);
        }
        map.put("results", file.getOriginalFilename());
        return "importResults";
    }

    @RequestMapping(value = "/import")
    public String importFile (Map<String, Object> map, HttpServletRequest request)
    {
        map.put("allEvents", getAllEventMap(eventService.allEvents()));
        map.put("importFile", new ImportFile());
        return "import";
    }
}
