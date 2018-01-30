/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uwe_allocator;

/**
 *
 * @author Umang
 */
class course_type {

    String course_code = null;
    String course_name = null;
    String course_type = null;
    int credits = 0;
    String pre_req = null;
    
    public course_type(String code, String name, String type, int num_credits, String prereq) {
        
        course_code = code;
        course_name = name;
        course_type = type;
        credits = num_credits;
        pre_req = prereq;
    }    
}
