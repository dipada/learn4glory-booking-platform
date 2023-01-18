const vue = new Vue({
    el: '#vue', data: {

        // Pages flags
        coursePage: false,
        teacherOfCoursePage: false,
        teacherSummaryPage: false,
        errorPage: false,
        loginPage: false,
        lessonSummaryPage: true,
        bookingPage: false,
        bookingSuccessPage: false,
        myBookingsPage: false,
        manageBookingPage: false,
        adminPage: false,
        managePage: false,
        manageLessonPage: false,
        manageCoursePage: false,
        manageTeacherPage: false,

        secondSelection: false,
        thirdSelection: false,
        fourthSelection: false,

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
        listAllBookingsAdmin: [],
        listTeachers: [],
        listTeachersComplete: [],
        listCoursesComplete: [],

        // admin operation
        teacherName: '',
        teacherSurname: '',
        courseTitle: '',
        teacherSelected: '',
        courseSelected: '',
        daySelected: '',
        hourSelected: '',

        // current selection
        currentCourseSelected: '',
        currentTeacherSelected: '',
        currentLessonToBook: '',
        dayHourBookingLess: '',
        currentBookingToManage: '',
        currentTeacherToDelete: '',
        currentCourseToDelete: '',

        // User data
        userSession: 'no session',
        userEmail: '',
        userPwd: '',
        userRole: '',
    },

    mounted() {
        //this.loadCourse();
        this.loadLessons();
        this.loadBookedLessons();
        this.initApp();
    },

    methods: {
        pp : function() {
            vue.jj = this.hourSelected + this.daySelected
        },

        // TODO funzione per status on refresh

        ppp: function () {
            console.log(" REFRESH")
        },

        initApp: function () {
            $.get('ServletSession', {action: 'imLogged'}, function (data) {
                if (data.userSession !== 'no session') {
                    vue.logged = true;

                    /* vue.userSession = data.userSession;
                     vue.userEmail = data.userEmail;
                     vue.userRole = data.userRole;*/
                    vue.reqUserInfo();
                    vue.showHomepage();
                } else {
                    vue.logged = false;
                }
            });
        },

        allPagesOff: function () {
            this.loginPage = false;
            this.teacherOfCoursePage = false;
            this.coursePage = false;
            this.teacherSummaryPage = false;
            this.lessonSummaryPage = false;
            this.errorPage = false;
            this.bookingPage = false;
            this.myBookingsPage = false;
            this.bookingSuccessPage = false;
            this.manageBookingPage = false;
            this.adminPage = false;
            this.manageLessonPage = false;
            this.managePage = false;
            this.manageCoursePage = false;
            this.manageTeacherPage = false;
        },

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
            console.log("da home page " + this.isAdmin() + " " + vue.userRole)
            this.showLessonSummaryPage();

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

        showManageBookingPage: function () {
            this.allPagesOff();
            this.manageBookingPage = true;
            this.activePage = 'manageBookingPage';
        },

        showLastPage: function () {
            this.allPagesOff();

            switch (this.activePage) {
                case 'homePage': {
                    this.courseList = '';
                    this.showHomepage();
                }
                    break;

                case 'coursePage': {
                    this.courseList = '';
                    this.showCoursePage();
                }
                    break;

                case 'teacherSummaryPage': {
                    this.listTeacherLessons = '';
                    this.listBookedLessons = '';
                    this.showTeacherSummaryPage();
                }
                    break;

                case 'teacherOfCoursePage': {
                    this.teachersOfCourse = '';
                    this.showTeacherOfCoursePage();
                }
                    break;

                case 'lessonSummaryPage': {
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

                case 'managePage':
                    this.showManagePage();
                    break;

                case 'manageLessonPage':
                    this.showManageLessonPage();
                    break;

                case 'manageCoursePage':
                    this.showManageCoursePage();
                    break;

                case 'manageTeacherPage':
                    this.showManageTeacherPage();
                    break;

                default:
                    this.showHomepage();
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

        showBookingPage: function () {
            this.allPagesOff();
            this.bookingPage = true;
            this.activePage = 'bookingPage';
        },

        showAdminPage: function () {
            this.allPagesOff();
            this.loadAllBookingsAdmin();
            this.adminPage = true;
            this.activePage = 'adminPage';
        },

        showManagePage: function () {
            this.allPagesOff();
            this.managePage = true;
            this.showManageCoursePage();
            this.activePage = 'managePage';
        },


        showManageCoursePage: function () {
            this.allPagesOff();
            this.managePage = true;
            this.manageCoursePage = true;
            this.activePage = 'manageCoursePage';
            this.loadDataManageCoursePage();
        },

        showManageTeacherPage: function () {
            this.allPagesOff();
            this.loadDataManageTeacherPage();
            this.managePage = true;
            this.manageTeacherPage = true;
            this.activePage = 'manageTeacherPage';
        },

        showManageLessonPage: function () {
            this.statusMsg = '';
            this.allPagesOff();
            this.loadDataManageLessonPage();
            this.managePage = true;
            this.manageLessonPage = true;
            this.activePage = 'manageLessonPage';
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
                            console.log(" IN LOGIN " + vue.userRole)
                            if (vue.fromReservePage) {
                                vue.showBookingPage();
                            } else {
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

        isAdmin: function () {
            console.log(" E ADMIN? " + vue.userRole)
            return vue.userRole === 'admin';
        },

        logout: function () {
            $.get('ServletSession', {action: 'logout'}, {}).always(function () {
                vue.setDefaultValues();
                location.reload();
            }).fail(function () {
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
            }).fail(function () {
                vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                vue.showErrorPage();
            });
        },

        loadTeachers: function () {
            if (this.userSession !== 'no session' && this.userRole === 'admin') {
                $.get('ServletAdmin', {action: 'loadAllTeachers', userSession: this.userSession}, function (data) {
                    vue.listTeachers = data;
                }).fail(function () {
                    vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                    vue.showErrorPage();
                });
            }
        },

        loadAllTeachers: function () {
            $.get('ServletAdmin', {action: 'loadTeachersComplete', userSession: this.userSession}, function (data) {
                vue.listTeachersComplete = data;
            }).fail(function () {
                vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                vue.showErrorPage();
            });
        },

        loadAllCourses: function () {
            $.get('ServletAdmin', {action: 'loadCoursesComplete', userSession: this.userSession}, function (data) {
                vue.listCoursesComplete = data;
            }).fail(function () {
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

        loadDataManageTeacherPage: function () {
            this.loadTeachers();
            this.loadAllTeachers();
        },

        loadDataManageCoursePage: function () {
            this.loadCourse();
            this.loadAllCourses();
        },

        loadDataManageLessonPage : function () {
          this.loadCourse();
          this.loadTeachers();
        },

        insertTeacher: function () {

            if (this.regExprCheck(this.teacherName, "[a-zA-Z ]{4,15}")) {
                if (this.regExprCheck(this.teacherSurname, "[a-zA-Z ]{4,15}")) {
                    if (this.userSession !== 'no session' && this.userRole === 'admin') {
                        $.post('ServletAdmin', {
                            action: 'insertTeacher',
                            userSession: this.userSession,
                            teacherName: this.teacherName,
                            teacherSurname: this.teacherSurname
                        }, function (data) {
                            if (data === 'true') {
                                vue.statusMsg = 'Insegnante ' + vue.teacherName + ' ' + vue.teacherSurname + ' inserito con successo';
                                vue.loadTeachers();
                                vue.showManageTeacherPage();
                            } else {
                                vue.statusMsg = 'Insegnante già presente';
                                vue.showErrorPage();
                            }
                        }).fail(function () {
                            vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                            vue.showErrorPage();
                        });
                    }

                } else {
                    vue.statusMsg = 'Cognome errato. Deve contenere almeno 4 lettere';
                }
            } else {
                vue.statusMsg = 'Nome errato. Deve contenere almeno 4 lettere';
            }
        },

        insertCourse: function () {

            if (this.regExprCheck(this.courseTitle, "[a-zA-Z ]{2,15}")) {
                    if (this.userSession !== 'no session' && this.userRole === 'admin') {
                        $.post('ServletAdmin', {
                            action: 'insertCourse',
                            userSession: this.userSession,
                            courseName: this.courseTitle,
                        }, function (data) {
                            if (data === 'true') {
                                vue.statusMsg = 'Corso ' + vue.courseTitle + ' inserito con successo';
                                vue.loadCourse();
                                vue.showManageCoursePage();
                            } else {
                                vue.statusMsg = 'Corso già presente';
                                vue.showErrorPage();
                            }
                        }).fail(function () {
                            vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                            vue.showErrorPage();
                        });
                    }
            } else {
                vue.statusMsg = 'Titolo corso non valido. Deve contenere almeno 2 lettere';
            }
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

        loadTeacherLessons: function (courseId, teacherId) {
            $.get('ServletDao', {
                action: 'lessonsOfTeacherCourse',
                teacherid: teacherId,
                courseid: courseId
            }, function (data) {
                vue.listTeacherLessons = data;
            });
        },

        loadUserBookings: function () {
            console.log("in loadbooking  + " + this.userSession + this.userRole);
            if (vue.userSession !== 'no session' && vue.userRole === 'client') {
                console.log("in loadbooking");
                $.get('ServletBooking', {action: 'userBookings', userSession: this.userSession}, function (data) {
                    vue.listUserBookings = data;
                    console.log("prenotazioni ");
                    console.log(vue.listUserBookings);
                });
            } else {
                vue.statusMsg = 'Operazione non consentita';
                vue.showErrorPage();
            }
        },

        loadAllBookingsAdmin: function () {
            if (vue.userSession !== 'no session' && vue.userRole === 'admin') {
                $.get('ServletBooking', {action: 'allBookingsAdmin', userSession: this.userSession}, function (data) {
                    vue.listAllBookingsAdmin = data;
                }).fail(function () {
                    vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                    vue.showErrorPage();
                });
            } else {
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

        isBooked: function (idLesson) {
            for (let i = 0; i < this.listBookedLessons.length; i++) {
                if (idLesson === this.listBookedLessons[i].lesson) {
                    return true;
                }
            }
            return false;
        },

        prepareReservation: function (lesson) {
            if (!this.logged) {
                this.errorMsg = 'Per prenotare devi autenticarti';
            }
            this.reserveClick = true;
            this.currentLessonToBook = lesson;
            switch (lesson.week_day) {
                case 'LUN':
                    this.dayHourBookingLess = "Lunedì";
                    break;

                case 'MAR':
                    this.dayHourBookingLess = "Martedì";
                    break;

                case 'MER':
                    this.dayHourBookingLess = "Mercoldì";
                    break;

                case 'GIO':
                    this.dayHourBookingLess = "Giovedì";
                    break;

                case 'VEN':
                    this.dayHourBookingLess = "Venerdì";
                    break;
            }

            this.dayHourBookingLess += " " + lesson.hour;

            console.log(lesson);
            this.selectionMsg = lesson.course.title + " - " + lesson.week_day + " - " + lesson.hour + " - " + (lesson.hour + 1) + " - " + lesson.teacher.name + " " + lesson.teacher.surname;
        },

        reserveSelectedLesson: function () {
            if (!this.logged) {
                this.fromReservePage = true;
                this.showLoginPage();
            } else {
                this.showBookingPage();
            }
        },

        cancelBooking: function () {
            this.fromReservePage = false;
            this.selectionMsg = '';
            this.dayHourBookingLess = '';
            this.currentLessonToBook = '';
            this.reserveClick = false;
            this.errorMsg = '';
            this.showTeacherSummaryPage();
        },

        makeBooking: function () {
            if (this.userSession !== 'no session') {
                $.post('ServletBooking', {
                    action: 'insertBooking',
                    userSession: this.userSession,
                    lessonid: this.currentLessonToBook.id_lesson
                }, function (data) {
                    if (JSON.stringify(data) !== '-1') {
                        vue.statusMsg = 'Prenotazione effettuata con successo!';
                        vue.showBookingSuccessPage();
                    } else {
                        vue.statusMsg = 'Non è possibile prenotare questa lezione. Hai già prenotato un\'altra lezione in questo orario';
                        vue.showErrorPage();
                    }
                });
            }
        },

        confirmBooking: function () {
            $.post('ServletBooking', {
                action: 'confirmBooking',
                userSession: this.userSession,
                bookingId: this.currentBookingToManage.id_booking
            }, function (data) {
                if (JSON.stringify(data) === 'true') {
                    console.log(" IN TRUE ")
                    vue.statusMsg = 'Prenotazione confermata con successo!';
                    vue.showBookingSuccessPage();
                } else {
                    vue.statusMsg = 'Non è possibile confermare questa prenotazione. Errore interno';
                    vue.showErrorPage();
                }
            });
        },

        deleteBooking: function () {
            $.post('ServletBooking', {
                action: 'deleteBooking',
                userSession: this.userSession,
                bookingId: this.currentBookingToManage.id_booking
            }, function (data) {
                if (JSON.stringify(data) === 'true') {
                    vue.statusMsg = 'Prenotazione cancellata con successo!';
                    vue.showBookingSuccessPage();
                } else {
                    vue.statusMsg = 'Non è possibile cancellare questa prenotazione. Errore interno';
                    vue.showErrorPage();
                }
            });
        },

        deleteTeacher: function () {
            if (this.userSession !== 'no session' && this.userRole === 'admin') {
                console.log("in deleteTeacher " + vue.currentTeacherToDelete);
                $.post('ServletAdmin', {
                    action: 'deleteTeacher',
                    userSession: this.userSession,
                    teacherId: this.currentTeacherToDelete.id_teacher
                }, function (data) {
                    if (JSON.stringify(data) === 'true') {
                        vue.statusMsg = '';
                        vue.showManageTeacherPage();
                        //vue.showTeacherSuccessPage();
                    } else {
                        vue.statusMsg = 'Non è possibile cancellare questo insegnante. Errore interno';
                        vue.showErrorPage();
                    }
                }).fail(function () {
                    vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                    vue.showErrorPage();
                });
            }
        },

        deleteCourse: function () {
            if (this.userSession !== 'no session' && this.userRole === 'admin') {
                $.post('ServletAdmin', {
                    action: 'deleteCourse',
                    userSession: this.userSession,
                    courseId: this.currentCourseToDelete.id_course
                }, function (data) {
                    if (JSON.stringify(data) === 'true') {
                        vue.statusMsg = '';
                        vue.showManageCoursePage();
                    } else {
                        vue.statusMsg = 'Non è possibile cancellare questo corso. Errore interno';
                        vue.showErrorPage();
                    }
                }).fail(function () {
                    vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                    vue.showErrorPage();
                });
            }
        },

        insertTeacherCourseAssociation: function () {
            if(this.teacherSelected === '' || this.courseSelected === '' || this.daySelected === '' || this.hourSelected === ''){
                this.statusMsg = 'Seleziona tutti i campi';
            }else{
                if(this.userSession !== 'no session' && this.userRole === 'admin'){
                    $.post('ServletAdmin', {
                        action: 'insertTeacherCourseAssociation',
                        userSession: this.userSession,
                        teacherId: this.teacherSelected.id_teacher,
                        courseId: this.courseSelected.id_course,
                        day: this.daySelected,
                        hour: this.hourSelected
                    }, function (data) {
                        if (data === 'true') {
                            vue.showManageLessonPage();
                            vue.statusMsg = 'Lezione' + vue.teacherSelected.name + ' ' + vue.teacherSelected.surname + ' - ' + vue.courseSelected.title + ' - ' + vue.daySelected + ' - ' + vue.hourSelected + ' inserita con successo';
                        } else {
                            vue.statusMsg = vue.teacherSelected.name + " " + vue.teacherSelected.surname + " è già impegnato in questo orario";
                            this.showErrorPage();
                        }
                    }).fail(function () {
                        vue.statusMsg = 'Qualcosa è andato storto :( il server ha smesso temporaneamente di rispondere';
                        vue.showErrorPage();
                    });
                }
            }
        },
    }
});
