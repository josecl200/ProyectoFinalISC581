package edu.pucmm.isc581.applogin.dataModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OsDataModel {
    private Integer image;
    private String name;
    private String api_level;
}
