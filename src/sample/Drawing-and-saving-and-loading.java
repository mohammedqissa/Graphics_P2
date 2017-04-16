///*
//التخطيط على الخلفيه
//
//*/
//	private void drawBG(WritableRaster r) {
//		/*
//		 * buffered Image
//		 */
//		int[] c = new int[] { 255, 255, 255 };
//		int[] c1 = new int[] { 20, 205, 255 };
//
//		for (int i = 0; i < img.getHeight(); i++) {
//
//			if (i % 16 == 0) {
//				for (int j = 0; j < img.getWidth(); j++) {
//					r.setPixel(j, i, c1);
//				}
//			} else {
//
//				for (int j = 0; j < img.getWidth(); j++) {
//					r.setPixel(j, i, c);
//				}
//			}
//
//		}
//		/*
//		 * JPanel
//		 */
//		canvas.revalidate();
//		canvas.repaint();
//	}
//
//    /*
//    الخط الاحمر
//
//    هاد عشان اعرف وين ارسم الخط : على اليمين او الشمال ... اكتبهم حسب وين بتستدعي الميثود
//    */
//
//    	if (language.equalsIgnoreCase("arabic") || language.equalsIgnoreCase("hebrew")) {
//			drawRedLine(img.getRaster(), 690);
//		} else {
//			drawRedLine(img.getRaster(), 18);
//		}
//
///*
//هاي الميثود نفسها
//*/
//    	private void drawRedLine(WritableRaster r, int y) {
//		for (int i = 0; i < img.getHeight(); i++) {
//			r.setPixel(y, i, new int[] { 255, 89, 89 });
//		}
//		canvas.revalidate();
//		canvas.repaint();
//	}
///*
// the save method
//*/
//
//	private void save(String name) throws IOException {
//		String name1 = name;
//		ImageIO.write(img, "PNG", new File(name1));
//
//        /*
//        y & x اللي تحت عشان اخزن اللغه اللي كتبت فيها وبخزن موقع
//        */
//		File f = new File(name+".INFO"+".txt");
//		BufferedWriter bf = new BufferedWriter(new FileWriter(f));
//
//		bf.write(selectedLanguage.getText());
//		bf.newLine();
//		bf.write("X:"+Xpos);
//		bf.newLine();
//		bf.write("Y:"+Ypos);
//		bf.close();
//	}
//
//    /*
//        the load method
//    */
//
//
//    	private void load(String name) throws IOException {
//        /*
//         select ==> هاد عشان يفحص اذا اللغه اللي قراها من الملف هي نفسها او لا , اذا لأ بخليه يختار خط للغه
//        */
//		boolean select = true ;
//        /*
//         ".INFO.txt" هون بقرأ الملف اللي فيه المعلومات , واللي مفروض يكون نفس اسم الصوره بس زياده عليه كلمة
//        */
//		String name1 = name+".INFO.txt" ;
//		System.out.println(name1);
//        /*
//        هون بقرأ الداتا
//        */
//		Scanner c = new Scanner(new FileReader(new File(name1)));
//		while(c.hasNext()){
//			String in = c.nextLine();
//			if(in.charAt(0) == 'X'){
//				StringTokenizer st = new StringTokenizer(in, ":");
//				st.nextToken();
//				Xpos = Integer.parseInt(st.nextToken());
//				System.out.println(Xpos);
//			}else if(in.charAt(0) == 'Y'){
//				StringTokenizer st = new StringTokenizer(in, ":");
//				st.nextToken();
//				Ypos = Integer.parseInt(st.nextToken());
//				System.out.println(Ypos);
//			}else{
//				if(selectedLanguage.getText().equalsIgnoreCase(in)){
//					select = false ;
//				}else{
//					selectedLanguage.setText(in);
//					select = true ;
//				}
//			}
//		}
//		c.close();
//		/*
//        هون بفتحله انه اليوزر يختار خط اذا اللغه مش نفسها او مش موجوده من الاساس
//        */
//		if(select == true){
//			JFileChooser jf = new JFileChooser();
//			jf.showOpenDialog(null);
//			File f = jf.getSelectedFile();
//			readFont(f);
//		}
//
//        /*
//          jpanel  هان بوخد الصوره اللي قرأها وبطبعها على ال
//        */
//		img = ImageIO.read(new File(name));
//
//        >>>>>>>>> /*the next 2 lines are VERY VERY important*/<<<<<<<<<<<<<<
//        canvas.revalidate();
//		canvas.repaint();
//	}
//
//
//_____________________                              _____________________
//`-._:  .:'   `:::  .:\           |\__/|           /::  .:'   `:::  .:.-'
//    \      :          \          |:   |          /         :       /
//     \     ::    .     `-_______/ ::   \_______-'   .      ::   . /
//      |  :   :: ::'  :   :: ::'  :   :: ::'      :: ::'  :   :: :|
//      |     ;::         ;::         ;::         ;::         ;::  |
//      |  .:'   `:::  .:'   `:::  .:'   `:::  .:'   `:::  .:'   `:|
//      /     :           :           :           :           :    \
//     /______::_____     ::    .     ::    .     ::   _____._::____\
//                   `----._:: ::'  :   :: ::'  _.----'
//                          `--.       ;::  .--'
//                              `-. .:'  .-'
//                                 \    /
//                                  \  /
//                                   \/
