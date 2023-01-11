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
    },

    mounted() {
        this.loadCourse();
    },

    methods: {

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

        login: function () {

            if (this.userEmail !== '' && this.userEmail !== null && this.userPwd !== '' && this.userPwd !== null && this.regExprCheck(this.userEmail, "[a-z0-9._%+-]+@[a-z0-9]+\\.[a-z]{2,4}") && this.regExprCheck(this.userPwd, "[A-Za-z0-9]{3,6}")) {

                if (this.userSession === 'no session') {
                    console.log("Controllo sueoratoi")
                    console.log(this.userEmail + " " + this.userPwd + " " + this.userSession);
                }

                if (vue.userSession === "no session") {
                    $.post('ServletSession', { action: 'login', userEmail: this.userEmail, userPwd: this.userPwd }, function (data) {
                        if (data === 'no session') {
                            vue.statusMsg = 'Email e password errati';
                        } else {
                            console.log("Nel else");
                            vue.userSession = data;
                            vue.logged = true;
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

        regExprCheck: function (string, pattern) {
            // verifica se stringa rispetta pattern
            var re = new RegExp(pattern);
            return (re.test(string));
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
