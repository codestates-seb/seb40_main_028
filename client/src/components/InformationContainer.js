import React, { useState } from 'react';
import styled from 'styled-components';
// import { BiLinkExternal } from 'react-icons/bi';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
// import Description from './Description';
import axios from 'axios';


import Toggle from '../components/Toggle';




const Container = styled.div`
  width: 300px;
  margin-bottom: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-shadow: rgba(0, 0, 0, 0.05) 0px 10px 24px 0px,
    rgba(0, 0, 0, 0.05) 0px 20px 48px 0px, rgba(0, 0, 0, 0.1) 0px 1px 4px 0px;
  border-radius: 10px;
`;

const MentDiv = styled.div`
  font-size: ${({ fontSize }) => fontSize || '13px'};
  margin-bottom: 10px;
  padding: ${({ padding }) => padding || 0};
  margin-top: ${({ mt }) => mt || 0};
  text-align: center;
  
`;

const MentSpan = styled.span`
  font-size: 13px;
  cursor: pointer;
  // & a {
  //   text-decoration: none;
  //   color: #0074cc;
  // }
  color: rgb(88 101 242);
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  height: 100%;
  width: 100%;
  padding: 20px 10px 10px 10px;
`;

const InputContainer = styled.div`
  margin: 0 0 5px 0;
  display: flex;
  flex-direction: column;
  align-self: center;
`;

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

const Input = styled.input`
  width: 260px;
  height: 35px;
  padding: 0;
  border: 2px solid rgb(88 101 242) ;
`;

const SubmitBtn = styled.input`
  width: 260px;
  height: 35px;
  font-size: 13.6px;
  font-weight: 600;
  color: white;
  background-color: rgb(88 101 242);

  border: 0;
  border-radius: 3px;
  padding: 0;
  margin-bottom: 5px;
  &:hover {
    background-color: rgb(71 82 196);
  }
`;

const RegisterMent = styled.div`
  padding: 0 18px;
  margin-bottom: 16px;
  color: #6a737c;
  font-size: 12px;
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
`;

// const Checkbox = styled.div`
//   padding: 0 18px;
//   margin-bottom: 14px;
//   display: flex;
//   align-items: flex-start;
//   & input {
//     margin-right: 4px;
//   }
//   & div {
//     margin: 2px 0 0 0;
//     font-size: 12px;
//   }
// `;

export default function InformationContainer() {
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({ mode: onchange });
  const onLogin = async (data) => {
    // console.log(data);
    // 회원가입 api 자리
    try {
      axios.post('#', { ...data });
    } catch (err) {
      setError(err);
    }
  };

  return (
    <MainContainer>
      {/* 왼쪽 내용 */}
      {/* <Description /> */}
      <div>
        <Container>
          <Form onSubmit={handleSubmit(onLogin)}>
           



            <InputContainer>
              <Label htmlFor={'height'}>height(키)</Label>
              <Input
                type={'text'}
                id="height"
                {...register('height', {
                  required: true,
                  minLength: 2,
                  maxLength: 3,
                })}
              />
              {errors.height && errors.height.type === 'required' && (
                <Errormsg>⚠ 키를 입력해주세요.</Errormsg>
              )}
              {errors.height && errors.height.type === 'minLength' && (
                <Errormsg>⚠ 올바른 키를 입력하세요</Errormsg>
              )}
              {errors.height && errors.height.type === 'maxLength' && (
                <Errormsg>⚠ 올바른 키를 입력하세요</Errormsg>
              )}
            </InputContainer>


            <InputContainer>
              <Label htmlFor={'weight'}>weight(몸무게)</Label>
              <Input
                type={'text'}
                id="weight"
                {...register('weight', {
                  required: true,
                  minLength: 2,
                  maxLength: 3,
                })}
              />
              {errors.weight && errors.weight.type === 'required' && (
                <Errormsg>⚠ 몸무게를 입력해주세요.</Errormsg>
              )}
              {errors.weight && errors.weight.type === 'minLength' && (
                <Errormsg>⚠ 올바른 몸무게를 입력하세요</Errormsg>
              )}
              {errors.weight && errors.weight.type === 'maxLength' && (
                <Errormsg>⚠ 올바른 몸무게를 입력하세요</Errormsg>
              )}
            </InputContainer>


            <InputContainer>
              <Label htmlFor={'age'}>age(나이)</Label>
              <Input
                type={'text'}
                id="age"
                {...register('age', {
                  required: true,
                  minLength: 2,
                  maxLength: 3,
                })}
              />
              {errors.age && errors.age.type === 'required' && (
                <Errormsg>⚠ 나이를 입력해주세요.</Errormsg>
              )}
              {errors.age && errors.age.type === 'minLength' && (
                <Errormsg>⚠ 올바른 나이를 입력하세요</Errormsg>
              )}
              {errors.age && errors.age.type === 'maxLength' && (
                <Errormsg>⚠ 올바른 나이를 입력하세요</Errormsg>
              )}
            </InputContainer>


            <Toggle />



            {/* 비밀번호 안내문구 */}
            {/* <RegisterMent>
              비밀번호는 최소 1개의 문자와 1개의 숫자를 포함하여 최소 8자
              이상이어야 합니다.
            </RegisterMent> */}
            {/* <Checkbox>
              <input type={'checkbox'} />
              <div>
                Opt-in to receive occasional product updates, user research
                invitations, company announcements, and digests.
              </div>
            </Checkbox> */}




            {/* 완료 버튼 */}
            <SubmitBtn type="submit" value={'완료'}></SubmitBtn>
            {error && <Errormsg>⚠ {error}</Errormsg>}
          </Form>
        </Container>
        {/* <MentDiv>
          이미 계정이 있습니까?
          <MentSpan onClick={() => navigate('/login')}> Log in</MentSpan>
        </MentDiv> */}
        {/* <MentDiv>
          Are you an employer?{' '}
          <MentSpan onClick={() => navigate('/login')}>
            Sign up on Talent <BiLinkExternal />
          </MentSpan>
        </MentDiv> */}
      </div>
    </MainContainer>
  );
}
