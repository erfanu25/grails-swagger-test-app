package com.hmtmcse.gstester.api

import com.hmtmcse.gs.GsApiActionDefinition
import com.hmtmcse.gs.data.ApiHelper
import com.hmtmcse.gs.data.GsApiResponseData
import com.hmtmcse.gs.data.GsParamsPairData
import com.hmtmcse.gs.model.CustomProcessor
import com.hmtmcse.gstester.Student


class StudentApiDefinitionService {

    StudentService studentService


    private GsApiActionDefinition readDefinition(){
        GsApiActionDefinition definition = new GsApiActionDefinition<Student>(Student)
        definition.includeOnlyProperty(["name", "email", "id"])
        return definition
    }


    GsApiActionDefinition list(){
        return readDefinition().successResponseAsList()
    }


    GsApiActionDefinition createStudent(){
        GsApiActionDefinition definition = new GsApiActionDefinition<Student>(Student)
        definition.addRequestProperty("name").setIsRequired()
        definition.addRequestProperty("email").setIsRequired()
        definition.addRequestProperty("password").setIsRequired()
        definition.addRequestProperty("identification").setIsRequired()
        definition.addResponseProperty("uuid")
        definition.successResponseAsMap()
        return definition
    }


    GsApiActionDefinition details(){
        GsApiActionDefinition definition = readDefinition()
        definition.addResponseProperty("identification")
        definition.addResponseProperty("uuid")
        definition.successResponseAsMap()
        return definition
    }


    GsApiActionDefinition update(){
        GsApiActionDefinition definition = new GsApiActionDefinition<Student>(Student)
        definition.addRequestProperty("email")
        definition.addRequestProperty("name")
        definition.successResponseFormat = GsApiResponseData.successMessage("Successfully Updated")
        return definition
    }

    GsApiActionDefinition delete(){
        GsApiActionDefinition definition = new GsApiActionDefinition<Student>(Student)
        definition.successResponseFormat = GsApiResponseData.successMessage("Successfully Deleted")
        return definition
    }


    GsApiActionDefinition custom(){
        GsApiActionDefinition definition = new GsApiActionDefinition()
        definition.addRequestProperty("name").setIsRequired()
        definition.addRequestProperty("email").setIsRequired()
        definition.addRequestProperty("password").setIsRequired()
        definition.addRequestProperty("identification").setIsRequired()
        definition.customProcessor = new CustomProcessor() {
            @Override
            GsApiResponseData process(GsApiActionDefinition actionDefinition, GsParamsPairData paramData, ApiHelper apiHelper) {
                def result =  studentService.save(paramData.params)
                return apiHelper.help.responseToApi(actionDefinition, result)
            }
        }
        definition.addResponseProperty("name")
        definition.addResponseProperty("uuid")
        definition.addResponseProperty("identification")
        definition.successResponseAsMap()
        definition.successResponseFormat = GsApiResponseData.successMessage("Successfully Created")
        return definition
    }
}
