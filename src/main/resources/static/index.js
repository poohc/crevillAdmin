import Vue from "vue";
import * as VeeValidate from 'vee-validate';
Vue.component(VeeValidate.ValidationProvider);
import $ from 'jQuery';
new Vue({
	el : '#page-body'
});