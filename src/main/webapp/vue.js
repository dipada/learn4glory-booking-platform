const vue = new Vue({
    el: '#vue', data: {

        // Pages flags
        coursePage: true,
        teacherOfCoursePage: false,
        teacherSummaryPage: false,
        errorPage: false,
        loginPage: false,
        lessonSummaryPage: false,
        bookingPage: false,
        bookingSuccessPage: false,
        myBookingsPage: false,

        // Logic flags
        logged: false,
        reserveClick: false,
        fromReservePage: false,

        // for fetch last page on server error
        activePage: '',

        // Messages
        statusMsg: '',
        selectionMsg: '',
        errorMsg: '',

        // Data lists
        listLessons: [],
        listTeacherLessons: [],
        courseList: [],
        teachersOfCourse: [],
        listBookedLessons: [],
        listUserBookings: [],

        // current selection
        currentCourseSelected: '',
        currentTeacherSelected: '',
        currentLessonToBook: '',
        dayHourBookingLess: '',

        // User data
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
            $.get('ServletSession', {action: 'imLogged'}, function (data) {
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

        allPagesOff : function (){
            this.loginPage = false;
            this.teacherOfCoursePage = false;
            this.coursePage = false;
            this.teacherSummaryPage = false;
            this.lessonSummaryPage = false;
            this.errorPage = false;
            this.bookingPage = false;
            this.myBookingsPage = false;
        },

        /*
        defaultValue : function (){

            this.activePage = '';

            this.reserveClick = false;
            this.fromReservePage = false;

            this.statusMsg = '';
            this.selectionMsg = '';
            this.errorMsg = '';

            this.listLessons = '';
            this.listTeacherLessons = '';
            this.courseList = '';
            this.teachersOfCourse = '';
            this.listBookedLessons = '';

            this.currentCourseSelected = '';
            this.currentTeacherSelected = '';
            this.currentLessonToBook = '';
            this.dayHourBookingLess = '';
        },

         */

        showLoginPage: function () {
            this.allPagesOff();
            this.loginPage = true;
            this.activePage = 'login';
        },

        showErrorPage: function () {
            this.allPagesOff();
            this.errorPage = true;
        },

        showHomepage: function () {
            this.showCoursePage();
            this.activePage = 'homePage';
        },

        showCoursePage: function () {
            this.allPagesOff();
            this.loadCourse();
            this.coursePage = true;
            this.activePage = 'coursePage';
        },

        showTeacherSummaryPage: function () {
            this.allPagesOff();
            this.teacherSummaryPage = true;
            this.activePage = 'teacherSummaryPage';

            this.loadTeacherLessons(this.currentCourseSelected.id_course, this.currentTeacherSelected.id_teacher);
            this.loadBookedLessons();
        },

        showTeacherOfCoursePage: function () {
            this.allPagesOff();
            this.teacherOfCoursePage = true;
            this.activePage = 'teacherOfCoursePage';

            this.loadTeachersOfCourse();
        },

        showLessonSummaryPage: function () {
            this.allPagesOff();
            this.lessonSummaryPage = true;
            this.activePage = 'lessonSummaryPage';

            this.loadLessons();
            this.loadBookedLessons();
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

              case 'bookingPage':
                  this.showBookingPage();
              break;

              case 'bookingSuccessPage':
                  this.showBookingSuccessPage();
              break;

              case 'myBookingsPage':
                    this.showMyBookingsPage();
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
            this.loadUserBookings();
        },

        showBookingPage : function (){
            this.allPagesOff();
            this.bookingPage = true;
            this.activePage = 'bookingPage';
        },


        login: function () {
            if (this.userEmail !== '' && this.userEmail !== null && this.userPwd !== '' && this.userPwd !== null && this.regExprCheck(this.userEmail, "[a-z0-9._%+-]+@[a-z0-9]+\\.[a-z]{2,4}") && this.regExprCheck(this.userPwd, "[A-Za-z0-9]{3,6}")) {
                if (vue.userSession === "no session") {
                    $.post('ServletSession', {
                        action: 'login', userEmail: this.userEmail, userPwd: this.userPwd
                    }, function (data) {

                        if (data === 'no session') {
                            vue.statusMsg = 'Email e password errati';
                        } else {
                            vue.userSession = data;
                            vue.logged = true;
                            vue.reqUserInfo();
                            console.log(" IN LOGIN " + this.fromReservePage)
                            if(vue.fromReservePage){
                                vue.showBookingPage();
                            }else {
                                vue.showHomepage();
                            }
                        }
                    }).fail(function () {
                        vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                        vue.showErrorPage();
                    });
                }
            } else {
                this.statusMsg = 'Email o password non validi';
            }
        },

        logout: function () {
            $.get('ServletSession', {action: 'logout'}, {}).always(function () {
                vue.setDefaultValues();
                location.reload();
            }).fail(function (){
                vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                vue.showErrorPage();
            });
        },

        reqUserInfo: function () {
            $.get('ServletSession', {action: 'reqUserInfo'}, function (data) {
                vue.userSession = data.userSession;
                vue.userEmail = data.userEmail;
                vue.userRole = data.userRole;
            }).fail(function () {
                vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                vue.showErrorPage();
            });
        },

        regExprCheck: function (string, pattern) {
            // verifica se stringa rispetta pattern
            var re = new RegExp(pattern);
            return (re.test(string));
        },

        setDefaultValues: function () {
            vue.userSession = 'no session';
            vue.userEmail = '';
            vue.userPwd = '';
            vue.userRole = '';
        },

        loadCourse: function () {
            $.get('ServletDao', {action: 'allCourse'}, function (data) {
                vue.courseList = data;
            }).fail(function (){
                vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                vue.showErrorPage();
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
            //TODO forward servlet
            console.log("Entrato in loadLesson")
            $.get('ServletDao', {action: 'lessons'}, function (data) {
                vue.listLessons = data;
                console.log(vue.listLessons);
            }).fail(function () {
                console.log("fallito")
            });
        },

        loadTeacherLessons : function(courseId, teacherId){
            $.get('ServletDao', {action: 'lessonsOfTeacherCourse', teacherid: teacherId, courseid: courseId}, function (data) {
                vue.listTeacherLessons = data;
            });
        },

        loadUserBookings: function () {
            console.log("in loadbooking  + " + this.userSession + this.userRole);
            if(vue.userSession !== 'no session' && vue.userRole === 'client') {
                console.log("in loadbooking");
                $.get('ServletBooking', {action: 'userBookings', userSession: this.userSession}, function (data) {
                    vue.listUserBookings = data;
                    console.log("prenotazioni ");
                    console.log(vue.listUserBookings);
                });
            }else{
                vue.statusMsg = 'Operazione non consentita';
                vue.showErrorPage();
            }
        },

        loadBookedLessons: function () {
            $.get('ServletDao', {action: 'allBookedLesson'}, function (data) {
                // console.log("DATI " + JSON.stringify(data));
                vue.listBookedLessons = data;
                console.log("quququu " + vue.listBookedLessons);
            });
        },

        isBooked : function (idLesson){
            for (let i = 0; i < this.listBookedLessons.length ; i++) {
                if(idLesson === this.listBookedLessons[i].lesson){
                    return true;
                }
            }
            return false;
        },

        prepareReservation : function (lesson){
            if(!this.logged){
                this.errorMsg = 'Per prenotare devi autenticarti';
            }
            this.reserveClick = true;
            this.currentLessonToBook = lesson;
            switch (lesson.week_day){
                case 'LUN': this.dayHourBookingLess = "Lunedì";
                break;

                case 'MAR': this.dayHourBookingLess = "Martedì";
                    break;

                case 'MER': this.dayHourBookingLess = "Mercoldì";
                    break;

                case 'GIO': this.dayHourBookingLess = "Giovedì";
                    break;

                case 'VEN': this.dayHourBookingLess = "Venerdì";
                    break;
            }

            this.dayHourBookingLess += " " + lesson.hour;

            console.log(lesson);
            this.selectionMsg = lesson.course.title + " - " + lesson.week_day + " - " + lesson.hour + " - " + (lesson.hour+1) + " - " + lesson.teacher.name + " " + lesson.teacher.surname;
        },

        reserveSelectedLesson : function (){
            if(!this.logged){
                this.fromReservePage = true;
                this.showLoginPage();
            }else{
                this.showBookingPage();
            }
        },

        cancelBooking : function (){
          this.fromReservePage = false;
          this.selectionMsg = '';
          this.dayHourBookingLess = '';
          this.currentLessonToBook = '';
          this.reserveClick = false;
          this.errorMsg = '';
          this.showHomepage();
        },

        makeBooking : function (){
            if(this.userSession !== 'no session') {
                $.post('ServletBooking', {action: 'insertBooking', userSession: this.userSession, lessonid: this.currentLessonToBook.id_lesson}, function (data) {
                    if (data !== '') {
                        vue.statusMsg = 'Prenotazione effettuata con successo!';
                        vue.showBookingSuccessPage();
                    } else {
                        vue.statusMsg = 'Non è possibile prenotare questa lezione. Errore interno';
                        vue.showErrorPage();
                    }
                });
            }
        }
    }
});
