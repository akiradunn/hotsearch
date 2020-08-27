package com.akiradunn.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Integer id;

    private String title;

    private Date createtime;

    public Record(String title){
        this.title = title;
    }
}