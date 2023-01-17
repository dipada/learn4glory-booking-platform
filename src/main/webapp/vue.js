const vue = new Vue({
    el: '#vue', data: {
        statusMsg: '',
        logged: false,
        coursePage: true,
        teacherOfCoursePage: false,
        errorPage: false,
        loginPage: false,
        listLessons: [],
        courseList: [],
        teachersOfCourse: [],
        currentCourseSelected: '',
        currentTeacherSelected: '',
        currentLessonSelected: '',
        userSession: 'no session',
        userEmail: '',
        userPwd: '',
        userRole: '',
    },

    mounted() {
        this.loadCourse();
        this.initApp();
    },

    methods: {
        // TODO funzione per status on refresh

        initApp: function () {
            $.get('ServletSession', {action: 'imLogged'}, function (data){
                if (data.userSession !== 'no session') {
                    vue.logged = true;

                   /* vue.userSession = data.userSession;
                    vue.userEmail = data.userEmail;
                    vue.userRole = data.userRole;*/
                    vue.reqUserInfo();
                } else {
                    vue.logged = false;
                }
            });
        },

        showLoginPage: function () {
            this.loginPage = true;
            this.coursePage = false;
            this.teacherOfCoursePage = false;
            this.errorPage = false;
        },

        showErrorPage: function () {
            this.errorPage = true;
            this.coursePage = false;
            this.teacherOfCoursePage = false;
            this.loginPage = false;
        },

        showHomepage: function () {
            this.loadCourse();
            this.showCoursePage();
        },

        showCoursePage: function () {
            this.coursePage = true;
            this.teacherOfCoursePage = false;
            this.errorPage = false;
            this.loginPage = false;
        },

        showLastPage : function () {
          this.allPagesOff();

          switch (this.activePage) {
              case 'homePage':{
                  this.courseList = '';
                  this.showHomepage();
              }
              break;

              case 'coursePage':{
                  this.courseList = '';
                  this.showCoursePage();
              }
              break;

              case 'teacherSummaryPage':{
                  this.listTeacherLessons = '';
                  this.listBookedLessons = '';
                  this.showTeacherSummaryPage();
              }
              break;

              case 'teacherOfCoursePage':{
                  this.teachersOfCourse = '';
                  this.showTeacherOfCoursePage();
              }
              break;

              case 'lessonSummaryPage':{
                  this.listLessons = '';
                  this.listBookedLessons = '';
                  this.showLessonSummaryPage();
              }
              break;

              case 'login':
                  this.showLoginPage();
              break;

              case 'bookingPage': this.showBookingPage();
              break;

                case 'bookingSuccessPage': this.showBookingSuccessPage();
                break;

                default: this.showHomepage();
          }
        },

        showBookingSuccessPage: function () {
            this.allPagesOff();
            this.bookingSuccessPage = true;
            this.activePage = 'bookingSuccessPage';
        },

        showMyBookingsPage: function () {
          this.allPagesOff();
          this.myBookingsPage = true;
          this.activePage = 'myBookingsPage';

          // TODO caricare prenotazioni utente
        },

        showBookingPage : function (){
            this.allPagesOff();
            this.bookingPage = true;
            this.activePage = 'bookingPage';
        },


        login: function () {

            if (this.userEmail !== '' && this.userEmail !== null && this.userPwd !== '' && this.userPwd !== null && this.regExprCheck(this.userEmail, "[a-z0-9._%+-]+@[a-z0-9]+\\.[a-z]{2,4}") && this.regExprCheck(this.userPwd, "[A-Za-z0-9]{3,6}")) {

                if (vue.userSession === "no session") {
                    $.post('ServletSession', { action: 'login', userEmail: this.userEmail, userPwd: this.userPwd }, function (data) {

                        if (data === 'no session') {
                            vue.statusMsg = 'Email e password errati';
                        } else {
                            vue.userSession = data;
                            vue.logged = true;
                            vue.reqUserInfo();
                            vue.showHomepage();
                        }
                    }).fail(function () {
                        vue.statusMsg = 'Il server non risponde';
                    });
                }
            } else {
                this.statusMsg = 'Email o password non validi';
            }
        },

        logout: function (){
          $.get('ServletSession', {action: 'logout'},{
          }).always(function (){
              console.log("chiusaa");
              vue.setDefaultValues();
              location.reload();
          });
        },

        reqUserInfo: function () {
            $.get('ServletSession', {action: 'reqUserInfo'}, function (data) {
                console.log("data print " + data);
                console.log("data 2 " + JSON.stringify(data));
                vue.userSession = data.userSession;
                console.log("userss " + vue.userSession);
                vue.userEmail = data.userEmail;
                vue.userRole = data.userRole;
                console.log("userEM " + vue.userEmail);
                console.log("userRO " + vue.userRole);
            });
        },

        regExprCheck: function (string, pattern) {
            // verifica se stringa rispetta pattern
            var re = new RegExp(pattern);
            return (re.test(string));
        },

        setDefaultValues: function (){
            vue.userSession = 'no session';
            vue.userEmail = '';
            vue.userPwd = '';
            vue.userRole = '';
        },

        loadCourse: function () {
            $.get('ServletDao', {action: 'allCourse'}, function (data) {
                vue.courseList = data;
            });
        },

        loadTeachersOfCourse: function () {
            console.log("in load");
            $.get('ServletDao', {
                action: 'teachersOfCourse', course_id: this.currentCourseSelected.id_course
            }, function (data) {
                vue.teachersOfCourse = data;
                if (vue.teachersOfCourse.length > 0) vue.statusMsg = 'Scegli il tuo insegnante'; else vue.statusMsg = 'Non ci sono insegnanti disponibili per questo corso :(';
            }).fail(function () {
                vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                vue.showErrorPage();
            })
        },

        loadLessons: function () {
            console.log("Entrato in loadLesson")
            $.get('ServletDao', {action: 'lessons'}, function (data) {
                vue.listLessons = data;
                console.log(vue.listLessons);
            }).fail(function () {
                console.log("fallito")
            });
        },
    }
});
