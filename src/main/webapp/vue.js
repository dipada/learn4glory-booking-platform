const vue = new Vue({
    el: '#vue', data: {
        logged: false,
        coursePage: true,
        teacherOfCoursePage: false,
        listLessons: [],
        courseList: [],
        teachersOfCourse: [],
        currentCourseSelected: '',
        currentTeacherSelected: '',
        currentLessonSelected: ''
    },

    mounted() {
        this.loadCourse();
    },

    methods: {

        loadHomepage: function () {
            this.displayCoursePage();
        },

        displayCoursePage: function () {
            this.coursePage = true;
            this.teacherOfCoursePage = false;
        },

        loadCourse: function () {
            $.get('ServletDao', {action: 'allCourse'}, function (data) {
                vue.courseList = data;
            });
        },

        loadTeachersOfCourse: function (){
            console.log("in load");
          $.get('ServletDao', {action: 'teachersOfCourse', course_id: this.currentCourseSelected.id_course}, function (data){
              vue.teachersOfCourse = data;
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
