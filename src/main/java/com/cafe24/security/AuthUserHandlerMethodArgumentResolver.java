package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.jblog.vo.UserVo;

// 이클래스는 파라미터를 위한 클래스이다. 파라미터만을 위해 작동한다.
public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		System.out.println("-------------------------AuthUserHandlerMethodArgumentResolver");
		
		if(supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	
	// 맨처음 특정 url로 들어오는 파라미터를 검사하기 위해 이 메소드가 실행된다.
	// 만약, true를 리턴하게 되면 resolveArgument를 수행한다.
	// false를 리턴하게 되면 아무것도 수행하지 않는다.
	// 이 정보가 계속 남아있게 되어 다음에 특정 url로 또 들어오게 될경우
	// 이전에 false를 리턴했으면 이 클래스는 또다시 동작하지 않는다.
	// 맨 처음 true를 리턴해야 이 클래스가 또 동작하는 것이다.
	// 맨 처음 이 클래스의 이 메소드가 동작하려면 Model이나 Redirectattri... 또는 annotation이 붙어있는 파라미터 이런게 아니라
	// Long, String 같은 일반 자료형이거나 UserVo같은 커스텀 자료형이어야 동작을 한다.
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		System.out.println("-------------------------AuthUserHandlerMethodArgumentResolver2");
		
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		//@AuthUser가 안붙어있음
		if (authUser == null) {
			System.out.println("-------------------------???");
			return false;
		}
		
		//파라키터 타입이 UserVO가 아님
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			System.out.println("-------------------------?????????");
			return false;
		}
		
		return true;
	}


}
