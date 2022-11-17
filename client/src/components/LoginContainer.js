import React, { useState } from 'react';
import styled from 'styled-components';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

// import Logo from '../assets/img/Logo.png';
const Container = styled.div`
  width: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 10px;
  align-items: center;
  background-color: white;
  box-shadow: rgba(0, 0, 0, 0.05) 0px 10px 24px 0px,
    rgba(0, 0, 0, 0.05) 0px 20px 48px 0px, rgba(0, 0, 0, 0.1) 0px 1px 4px 0px;
  border-radius: 10px;
`;

// const MentDiv = styled.div`
//   font-size: 13px;
//   margin-bottom: 10px;
// `;


// sing up + 구글 로그인 버튼 들어있는 태그
const BtnContainer = styled.div`

  padding: 20px;

  // 회원가입 + 구글 로그인 버튼 가로 사이즈 조정
  width: 210px;
  height: 200px;
  
  // 배경색
  // background-color: #ffffff;
  border-radius: 10px;

  //버튼 가운데 정렬
  // display: flex;
  // align-items: center;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

`;


// sign up 버튼
const SignUp = styled.span`
  // font-size: 13px;
  // color: #0074cc;
  // cursor: pointer;

  width: 100%;
  height: 30%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  box-shadow: rgba(255, 255, 255, 0.4) 0px 1px 0px 0px inset;
  font-weight: 600;
  border: 2px solid ;
  border-radius: 5px;

  margin-bottom: 20px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background-color: rgb(71 82 196);
    color:white;
  }

`;

// 구글 로그인
// GoogleLogin
const GoogleLogin = styled.span`
  // font-size: 13px;
  // color: #0074cc;
  // cursor: pointer;

  width: 100%;
  height: 30%;
  padding: 10px;
  color: rgb(88 101 242);
  background: white;
  font-size: 13px;
  // box-shadow: rgba(255, 255, 255, 0.4) 0px 1px 0px 0px inset;
  font-weight: 600;
  // border: 0px;
  border: 2px solid ;
  border-radius: 5px;

  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    background-color: rgb(71 82 196);
    color:white;
  }

  // 삭제 할것
  // &:hover {
  //   background-color: rgb(241, 242, 244);
  //   // color:white;
  // }


`;




// 로그인 form 
const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  height: 100%;
  width: 100%;
  padding: 10px;

  // form 전체 보라색 Email or 입력창 등등
  // color: rgb(88 101 242);

`;

const InputContainer = styled.div`
  margin: 10px 0;
  display: flex;
  flex-direction: column;
  align-self: center;
  
`;

// 입력에러 메세지 ex) 이메일을 입력해주세요
const Errormsg = styled.p`
  color: #bf1650;
  margin: 3px;
  font-size: 13px;
  
`;

const Label = styled.label`
  font-size: 15px;
  font-weight: 500;
  margin-bottom: 3px;
`;

// 이메일 + 패스워드 입력 창
const Input = styled.input`
  width: 260px;
  height: 35px;
  padding: 0;
  border: 2px solid rgb(88 101 242) ;
`;

// login 버튼
const SubmitBtn = styled.input`
  width: 260px;
  height: 35px;
  font-size: 13.6px;
  font-weight: 600;
  color: white;
  background-color: rgb(88 102 242);
  border: 0;
  border-radius: 3px;
  padding: 0;
  margin-bottom: 5px;
  &:hover {
    background-color: rgb(71 82 196);
  }
`;

// const LogoPng = styled.img`
//   height: 37px;
//   width: 32px;
//   margin-bottom: 20px;
// `;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;










export default function LoginContainer() {
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({ mode: onchange });
  const onLogin = async (data) => {
    // console.log(data);
    //로그인 api 자리
    try {
      console.log(data);
    } catch (err) {
      setError(err);
    }
  };
  return (
    <MainContainer>
      {/* <LogoPng src={Logo} /> */}
      
      <Container>
        <Form onSubmit={handleSubmit(onLogin)}>
          <InputContainer>
            <Label htmlFor={'Email'}>Email</Label>
            <Input
              type={'email'}
              id="Email"
              {...register('email', {
                required: true,
                pattern: /^\S+@\S+$/i,
                maxLength: 50,
              })}
            />
            {errors.email && errors.email.type === 'required' && (
              <Errormsg>⚠ 이메일을 입력해주세요.</Errormsg>
            )}
            {errors.email && errors.email.type === 'pattern' && (
              <Errormsg>⚠ 이메일 형식이여야 합니다.</Errormsg>
            )}
            {errors.email && errors.email.type === 'maxLength' && (
              <Errormsg>⚠ 최대 길이는 50자 이하여야 합니다</Errormsg>
            )}
          </InputContainer>
          <InputContainer>
            <Label htmlFor={'password'}>Password</Label>
            <Input
              type={'password'}
              id="password"
              {...register('password', {
                required: true,
                minLength: 10,
              })}
            />
            {errors.password && errors.password.type === 'required' && (
              <Errormsg>⚠ 패스워드를 입력해주세요</Errormsg>
            )}
            {errors.password && errors.password.type === 'minLength' && (
              <Errormsg>⚠ 최소 길이는 10자 이상이여야 합니다</Errormsg>
            )}
          </InputContainer>
          <SubmitBtn type="submit" value={'Login'}></SubmitBtn>
          {error && <Errormsg>⚠ {error}</Errormsg>}
        </Form>
      </Container>
      <BtnContainer>

        <SignUp onClick={() => navigate('/register')}>Sign up</SignUp>
        <GoogleLogin>
          <svg aria-hidden="true" className="native svg-icon iconGoogle" width="18" height="18" viewBox="0 0 18 18">
            <path d="M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 0 0 2.38-5.88c0-.57-.05-.66-.15-1.18Z" fill="#4285F4"></path><path d="M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 0 1-7.18-2.54H1.83v2.07A8 8 0 0 0 8.98 17Z" fill="#34A853"></path><path d="M4.5 10.52a4.8 4.8 0 0 1 0-3.04V5.41H1.83a8 8 0 0 0 0 7.18l2.67-2.07Z" fill="#FBBC05"></path><path d="M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 0 0 1.83 5.4L4.5 7.49a4.77 4.77 0 0 1 4.48-3.3Z" fill="#EA4335"></path>
          </svg>
          Sign up with Google</GoogleLogin>
      </BtnContainer>
      {/* <MentDiv>
        Don{"'"}t have an account?{' '}
      </MentDiv> */}
      {/* <MentDiv>
        Are you an employer?{' '}
        <SignUp onClick={() => navigate('/register')}>
          Sign up on Talent <BiLinkExternal />
        </SignUp>
      </MentDiv> */}
    </MainContainer>
  );
}