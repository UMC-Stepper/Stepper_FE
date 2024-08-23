## Stepper
<p align="center">
  <img src="https://github.com/user-attachments/assets/ce26efda-b9ec-4add-ad2b-1413257446b6" alt="스태퍼"/>
</p>

<div align="center">
  나만의 운동을 추가하고<br>
  운동카드를 추가하여<br>
  원하는 재활 운동을 해보세요
</div>

## 목차
  - [개요](#개요) 
  - [설명](#설명)
  - [기술스택](#기술스택)
  - [화면](#플로우)

## 개요
- 프로젝트 이름: STEPPER
- 프로젝트 지속기간: 2024.07.07-2024.08.20
- 개발 엔진 및 언어: Android Studio, Kotlin
- 멤버: 박지원 (루피), 이채영 (채리), 김성민 (미니)

## 설명
| 화면                      | 사진                                                    |
|---------------------------|---------------------------------------------------------|
| 회원가입/로그인           | <img src="https://github.com/user-attachments/assets/1e55328b-5485-4692-818e-26191a6c77b4" alt="로그인 (시작화면)" width="150"/> <img src="https://github.com/user-attachments/assets/398fae89-9e98-419f-ad5b-33ce7b15ebb1" alt="회원가입 3" width="150"/> <img src="https://github.com/user-attachments/assets/b0b197bc-a7a0-4c3b-9cdd-afc678812629" alt="회원가입 3" width="150"/> <img src="https://github.com/user-attachments/assets/af3acc2a-63fb-45dd-a10c-f8e4ef7cd36b" alt="회원가입 2" width="150"/> |
| 투데이 홈                | <img src="https://github.com/user-attachments/assets/db03fce3-65fd-4267-bd38-406c68478cb2" alt="투데이 홈 카드 X" width="150"/> <img src="https://github.com/user-attachments/assets/9ab2dad2-908f-4957-9506-910aa2d13967" alt="투데이 홈 카드 o" width="150"/> |
| 투데이 나만의 운동 추가   | <img src="https://github.com/user-attachments/assets/db26f6b3-0d20-496a-91a4-bd292ba2132d" alt="나만의 운동 추가 화면" width="150"/> <img src="https://github.com/user-attachments/assets/d71d9474-df83-46f6-95d3-f92b0a13777a" alt="나만의 운동 스크랩 목록 X" width="150"/> <img src="https://github.com/user-attachments/assets/3d988a75-ebc7-42d1-8410-7e6e4c6672a3" alt="나만의 운동 스크랩 목록 O" width="150"/> <img src="https://github.com/user-attachments/assets/3351da13-b49a-499b-880e-4e9a69bfc33d" alt="나만의 운동 추가 링크로 불러온 화면" width="150"/> |
| 투데이 운동 카드 작성     | <img src="https://github.com/user-attachments/assets/5641cc1e-f86e-4df6-afcb-98b25ff1e770" alt="운동 카드 추가 1 X" width="150"/> <img src="https://github.com/user-attachments/assets/c6e20ff7-5e81-4bf8-8464-c83f92ad8a71" alt="운동카드 추가 1 o" width="150"/> <img src="https://github.com/user-attachments/assets/4f3d8ebd-bc94-42e3-a615-0e6c56c329e3" alt="운동카드 추가 2" width="150"/> <img src="https://github.com/user-attachments/assets/84a19194-254a-4ee5-b61a-ff1c5e51f647" alt="운동카드 추가 3" width="150"/> |
| 투데이 평가일지 조회      | <img src="https://github.com/user-attachments/assets/b4721721-6eb4-49f1-9ca4-1df949ecc81d" alt="투데이 평가 일지 달력" width="150"/> <img src="https://github.com/user-attachments/assets/4cf37c35-4225-42bb-947e-20e715287615" alt="투데이 평가일지 조회" width="150"/> |
| 스태퍼 홈 / 추가운동 홈   | <img src="https://github.com/user-attachments/assets/591ff40c-8c6b-4477-801a-041f53ef1b6d" alt="스태퍼 홈" width="150"/> <img src="https://github.com/user-attachments/assets/2058c751-3de9-496e-8610-8eee1838a432" alt="스태퍼 추가 운동" width="150"/> |
| 스태퍼 운동하기           | <img src="https://github.com/user-attachments/assets/e9b6882d-ece3-4b90-b430-c387ca774559" alt="스태퍼 운동하기" width="150"/> <img src="https://github.com/user-attachments/assets/a4cd641e-d1b2-4ffa-b2c9-dab7157dd01e" alt="스태퍼 운동 완료" width="150"/> |
| 스태퍼 평가일지 작성      | <img src="https://github.com/user-attachments/assets/fe5890de-ea3e-4307-aba6-a97ede03d39c" alt="스태퍼 평가일지 작성" width="150"/> <img src="https://github.com/user-attachments/assets/b1b9f8d2-7d4c-4751-9623-0cc01af16cfa" alt="스태퍼 평가 일지 사진 촬영" width="150"/> <img src="https://github.com/user-attachments/assets/68983928-303e-467b-a2bc-2940c80b1f8b" alt="스태퍼 평가일지 사진 촬영 완료" width="150"/> |
| 커뮤니티                  | <img src="https://github.com/user-attachments/assets/4c4d8e0f-fd95-4eeb-98b2-98a403585ad4" alt="커뮤니티 홈" width="150"/> <img src="https://github.com/user-attachments/assets/495ab421-6047-4c58-ad56-45d0e56cf8d3" alt="위클리 게시판 홈" width="150"/> <img src="https://github.com/user-attachments/assets/7c4425e7-4064-4401-b1fb-60c0a31ddd32" alt="운동 부위 게시판 홈" width="150"/> |
| 뱃지                      | <img src="https://github.com/user-attachments/assets/8d2f340c-d88c-4164-96ed-9baedb40b4eb" alt="뱃지 1" width="150"/> <img src="https://github.com/user-attachments/assets/addcb318-d592-4fa2-8228-ab5bb46a6c53" alt="뱃지 2" width="150"/> |




설명

## 기술스택

### **🤖** 안드로이드
| **Category** | **TechStack** |
| --- | --- |
| Architecture | Clean Architecture, MVVM |
| DI | Hilt |
| Network | Retrofit, OkHttp, Gson |
| Asynchronous | Coroutines, Flow |
| Jetpack |  DataBinding, Navigation, DataStore, CameraX |
| Image | Glide |
| Notification | Firebase FCM |
| Open Source | Material CalendarView |

## 플로우
- 맵의 이동 가능 장소

|Home|Today|Stepper|community|
|---|---|---|---|
|![image](이미지)|![image](이미지)|![image](이미지)|![image](이미지)|
|설명|설명|설명|설명|

