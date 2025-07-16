### **🌳 브랜치 전략**

- `main`: 배포용 브랜치
- `develop`: 개발용 브랜치
- `feature/xxx`: 새 기능 개발
- `release/xxx`: 배포 전 마지막 점검
- `hotfix/xxx`: 배포 후 버그 긴급 수정
- 기능 개발은 `feature/기능명` 브랜치에서 해 주세요.
- 기능 이름은 소문자 + `-` 로 통일해주세요.

예시:

```
$git checkout -b feature/login
$git checkout -b feature/board-details
```

---

### **📦 커밋 컨벤션**
- ✨`feat` : 새로운 기능 추가
- 🐛`fix` : 버그 수정
- 📚`docs` : 문서 수정
- 💄`style` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- ♻️`refactor` : 코드 리펙토링
- ✅`test` : 테스트 코드, 리펙토링 테스트 코드 추가
- 💚`chore` : 빌드 업무 수정, 패키지 매니저 수정

**🧾 커밋 메시지 형식**
```
[GitMoji] 변경사항 요약
- 변경사항1
- 변경사항2
- ...
- issue : #번호
```

예시:

```
✨ 로그인 기능 구현 , 🐛 회원가입 기능 수정
- 로그인 메소드를 작성하였습니다.
- 인증 필터를 추가하였습니다.
- issue: #번호
```

---

### **✨ 코드 컨벤션**

팀원 모두가 일관된 스타일로 작성할 수 있도록 아래의 규칙을 지켜 주세요.

**✅ 공통 스타일**

- 들여쓰기: **4칸 스페이스**
- 중괄호 열기 `{`는 붙여서 사용하기
- 한 줄에 여러 문장 금지
- 메소드 체이닝 할 때 보기좋게 들여쓰기 해주기
- 불필요한 줄임말, 축약 지양 (ex. `usr`, `cnt` 대신 `user`, `count`)

**✅ 클래스/메서드/변수 네이밍**

| **대상** | **규칙** | **예시** |
| --- | --- | --- |
| 클래스 | **UpperCamelCase** | `UserService`, `AccountController` |
| 메서드 | **lowerCamelCase** | `getUserById()`, `registerUser()` |
| 변수 | **lowerCamelCase** | `userName`, `accountList` |
| 상수 | **UPPER_SNAKE_CASE** | `MAX_LIMIT`, `DEFAULT_TIMEOUT` |

**✅ 주석 스타일**

- 설명이 필요한 로직에는 한 줄 주석 추가
- TODO, FIXME 태그는 꼭 설명과 함께 작성

예시:

`// TODO: 로그인 실패시 리다이렉션 로직 수정 필요
// FIXME: 예외가 발생해도 로그가 안 찍힘. Logger 수정 필요`

---

### 💡 이슈 작성 팁

- 이슈 마지막 줄 그림

<img width="777" height="135" alt="image" src="https://github.com/user-attachments/assets/41737f70-caf5-45fb-b205-a793f58eb4aa" />

- `assignee` -> 자기 자신 혹은 같이 하시는분 등록
- `label & issue type` -> 이슈의 타입 또는 속성(예시 : 개발일 경우 enhancement)

<img width="424" height="642" alt="image" src="https://github.com/user-attachments/assets/0d7e5462-1cd2-4ef3-84d9-fc41e633478e" />

- 여기서 프로젝트에 자세한 내용 기입
- `priority` -> 우선순위(낮을 수록 우선순위가 높음)
- `size` -> 개발 기능의 크기
- `estimate` -> 개발 작업 예측량 ex) 3h 3d
- `iteration` -> 스프린트, 짧은 기간 동안 목표를 정해 집중해서 작업하는 개발 주기
- `start & end day` -> 개발 기간
