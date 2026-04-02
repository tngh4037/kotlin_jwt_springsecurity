package com.example.demo.common.exception

import com.example.demo.common.dto.BaseResponse
import com.example.demo.common.status.ResultCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    // Controller - DTO 에 적용된 Validation error 시  ( 참고. 아래 유효하지 않은 요청시 응답 예시 )
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach{ error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }

        return ResponseEntity(
            BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg),
            HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidInputException::class)
    protected fun invalidInputException(ex: InvalidInputException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf(ex.fieldName to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg),
            HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    protected fun defaultException(ex: Exception): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf("미처리 에러" to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg),
            HttpStatus.BAD_REQUEST)
    }
}

/**
 * {
 *     "timestamp": "2026-04-02T17:07:30.544+00:00",
 *     "status": 400,
 *     "error": "Bad Request",
 *     "trace": "org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public java.lang.String com.example.demo.member.controller.MemberController.signUp(com.example.demo.member.dto.MemberDtoRequest) with 6 errors: [Field error in object 'memberDtoRequest' on field '_password': rejected value [null]; codes [NotBlank.memberDtoRequest._password,NotBlank._password,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [memberDtoRequest._password,_password]; arguments []; default message [_password]]; default message [공백일 수 없습니다]] [Field error in object 'memberDtoRequest' on field '_email': rejected value [null]; codes [NotBlank.memberDtoRequest._email,NotBlank._email,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [memberDtoRequest._email,_email]; arguments []; default message [_email]]; default message [공백일 수 없습니다]] [Field error in object 'memberDtoRequest' on field '_birthDate': rejected value [null]; codes [NotBlank.memberDtoRequest._birthDate,NotBlank._birthDate,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [memberDtoRequest._birthDate,_birthDate]; arguments []; default message [_birthDate]]; default message [공백일 수 없습니다]] [Field error in object 'memberDtoRequest' on field '_loginId': rejected value [null]; codes [NotBlank.memberDtoRequest._loginId,NotBlank._loginId,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [memberDtoRequest._loginId,_loginId]; arguments []; default message [_loginId]]; default message [공백일 수 없습니다]] [Field error in object 'memberDtoRequest' on field '_name': rejected value [null]; codes [NotBlank.memberDtoRequest._name,NotBlank._name,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [memberDtoRequest._name,_name]; arguments []; default message [_name]]; default message [공백일 수 없습니다]] [Field error in object 'memberDtoRequest' on field '_gender': rejected value [null]; codes [NotBlank.memberDtoRequest._gender,NotBlank._gender,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [memberDtoRequest._gender,_gender]; arguments []; default message [_gender]]; default message [공백일 수 없습니다]] \r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:159)\r\n\tat org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:227)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:181)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:991)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:896)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:138)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:138)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:138)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:138)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:138)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:165)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:88)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:492)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:113)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:83)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:72)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:399)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:903)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1775)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:973)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:491)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)\r\n\tat java.base/java.lang.Thread.run(Thread.java:833)\r\n",
 *     "message": "Validation failed for object='memberDtoRequest'. Error count: 6",
 *     "errors": [
 *         {
 *             "objectName": "memberDtoRequest",
 *             "field": "_password",
 *             "rejectedValue": null,
 *             "codes": [
 *                 "NotBlank.memberDtoRequest._password",
 *                 "NotBlank._password",
 *                 "NotBlank"
 *             ],
 *             "arguments": [
 *                 {
 *                     "codes": [
 *                         "memberDtoRequest._password",
 *                         "_password"
 *                     ],
 *                     "arguments": null,
 *                     "defaultMessage": "_password",
 *                     "code": "_password"
 *                 }
 *             ],
 *             "defaultMessage": "공백일 수 없습니다",
 *             "bindingFailure": false,
 *             "code": "NotBlank"
 *         },
 *         {
 *             "objectName": "memberDtoRequest",
 *             "field": "_email",
 *             "rejectedValue": null,
 *             "codes": [
 *                 "NotBlank.memberDtoRequest._email",
 *                 "NotBlank._email",
 *                 "NotBlank"
 *             ],
 *             "arguments": [
 *                 {
 *                     "codes": [
 *                         "memberDtoRequest._email",
 *                         "_email"
 *                     ],
 *                     "arguments": null,
 *                     "defaultMessage": "_email",
 *                     "code": "_email"
 *                 }
 *             ],
 *             "defaultMessage": "공백일 수 없습니다",
 *             "bindingFailure": false,
 *             "code": "NotBlank"
 *         },
 *         {
 *             "objectName": "memberDtoRequest",
 *             "field": "_birthDate",
 *             "rejectedValue": null,
 *             "codes": [
 *                 "NotBlank.memberDtoRequest._birthDate",
 *                 "NotBlank._birthDate",
 *                 "NotBlank"
 *             ],
 *             "arguments": [
 *                 {
 *                     "codes": [
 *                         "memberDtoRequest._birthDate",
 *                         "_birthDate"
 *                     ],
 *                     "arguments": null,
 *                     "defaultMessage": "_birthDate",
 *                     "code": "_birthDate"
 *                 }
 *             ],
 *             "defaultMessage": "공백일 수 없습니다",
 *             "bindingFailure": false,
 *             "code": "NotBlank"
 *         },
 *         {
 *             "objectName": "memberDtoRequest",
 *             "field": "_loginId",
 *             "rejectedValue": null,
 *             "codes": [
 *                 "NotBlank.memberDtoRequest._loginId",
 *                 "NotBlank._loginId",
 *                 "NotBlank"
 *             ],
 *             "arguments": [
 *                 {
 *                     "codes": [
 *                         "memberDtoRequest._loginId",
 *                         "_loginId"
 *                     ],
 *                     "arguments": null,
 *                     "defaultMessage": "_loginId",
 *                     "code": "_loginId"
 *                 }
 *             ],
 *             "defaultMessage": "공백일 수 없습니다",
 *             "bindingFailure": false,
 *             "code": "NotBlank"
 *         },
 *         {
 *             "objectName": "memberDtoRequest",
 *             "field": "_name",
 *             "rejectedValue": null,
 *             "codes": [
 *                 "NotBlank.memberDtoRequest._name",
 *                 "NotBlank._name",
 *                 "NotBlank"
 *             ],
 *             "arguments": [
 *                 {
 *                     "codes": [
 *                         "memberDtoRequest._name",
 *                         "_name"
 *                     ],
 *                     "arguments": null,
 *                     "defaultMessage": "_name",
 *                     "code": "_name"
 *                 }
 *             ],
 *             "defaultMessage": "공백일 수 없습니다",
 *             "bindingFailure": false,
 *             "code": "NotBlank"
 *         },
 *         {
 *             "objectName": "memberDtoRequest",
 *             "field": "_gender",
 *             "rejectedValue": null,
 *             "codes": [
 *                 "NotBlank.memberDtoRequest._gender",
 *                 "NotBlank._gender",
 *                 "NotBlank"
 *             ],
 *             "arguments": [
 *                 {
 *                     "codes": [
 *                         "memberDtoRequest._gender",
 *                         "_gender"
 *                     ],
 *                     "arguments": null,
 *                     "defaultMessage": "_gender",
 *                     "code": "_gender"
 *                 }
 *             ],
 *             "defaultMessage": "공백일 수 없습니다",
 *             "bindingFailure": false,
 *             "code": "NotBlank"
 *         }
 *     ],
 *     "path": "/api/member/signup"
 * }
 */


// ===============================

// 예외처리 적용 후

/**
 * // 전체 공백 요청시
 * {
 *     "resultCoe": "ERROR",
 *     "data": {
 *         "_password": "공백일 수 없습니다",
 *         "_loginId": "공백일 수 없습니다",
 *         "_gender": "공백일 수 없습니다",
 *         "_name": "공백일 수 없습니다",
 *         "_birthDate": "공백일 수 없습니다",
 *         "_email": "공백일 수 없습니다"
 *     },
 *     "message": "에러가 발생했습니다."
 * }
 *
 * // 비밀번호가 유효하지 않을시 (커스텀 validator 참고)
 * {
 *     "resultCoe": "ERROR",
 *     "data": {
 *         "_password": "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
 *     },
 *     "message": "에러가 발생했습니다."
 * }
 *
 * // 서비스 계층에서 예외 던질 때 (중복 가입)
 * {
 *     "resultCoe": "ERROR",
 *     "data": {
 *         "loginId": "이미 등록된 ID 입니다."
 *     },
 *     "message": "에러가 발생했습니다."
 * }
 *
 * // 성공시
 * {
 *     "resultCoe": "SUCCESS",
 *     "data": null,
 *     "message": "회원가입이 완료되었습니다."
 * }
 */