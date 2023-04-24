package com.dopamines.backend.user.controller;

import com.dopamines.backend.user.service.UserService;
import com.fasterxml.jackson.core.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonParser;


import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoLoginController {

    private Logger log = LoggerFactory.getLogger(this.getClass());


}
