package semi.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semi.notice.model.service.NoticeService;
import semi.notice.model.vo.Notice;

/**
 * Servlet implementation class AdminNoticeDetailController
 */
@WebServlet("/admindetail.no")
public class AdminNoticeDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminNoticeDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			
				int noticeNo = Integer.parseInt(request.getParameter("nno")); 
				
				
				
				int result = new NoticeService().increaseCount(noticeNo);
				
				
				if(result > 0) {
					
					Notice n = new NoticeService().selectNotice(noticeNo);
					
					request.setAttribute("n", n);
					request.getRequestDispatcher("views/admin/adminNoticeDetail.jsp").forward(request, response);
					
					
				} else { 
					request.setAttribute("errorMsg", "공지사항 상세조회 실패");
					request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
				}
			}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
