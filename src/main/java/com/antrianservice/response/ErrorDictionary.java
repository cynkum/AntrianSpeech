package com.antrianservice.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "error_dictionary")
public class ErrorDictionary {
    @Id
    @Column(name = "key_message")
    private String keyMessage;

    @Column(name = "err_code")
    private String errCode;

    @Column(name = "err_msg_in")
    private String errorMsgIn;

    @Column(name = "err_msg_en")
    private String errorMsgEn;


}
