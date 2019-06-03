# FlexibleToolBar

- How to use it in project?
1. Add the repository to your build file:
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

2. Add the dependency:
dependencies {
	        implementation 'com.github.youvita:FlexibleToolBar:1.0.0'
	}
  
3. Add to xml layout:
<com.flexible.toolbar.library.FlexibleToolBar
     android:id="@+id/biz_toolbar"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"/>
     
4. Call in class java:
FlexibleToolBar mToolbar = findViewById(R.id.biz_toolbar);
        mToolbar.setToolBarTitle("FlexibleToolBar");
        mToolbar.setToolBarButton(FlexibleToolBar.GB_LEFT, android.R.drawable.ic_menu_add, 1, 20, 0);
        mToolbar.setToolBarButton(FlexibleToolBar.GB_LEFT, "Add", 2,10,0);
        mToolbar.setToolBarButton(FlexibleToolBar.GB_RIGHT, "Cancel", 1,0,20);

        mToolbar.setOnToolBarClickListener(new FlexibleToolBar.BizToolBarListener() {
            @Override
            public void onToolBarClicked(int groupButton, int id) {
                switch (groupButton) {
                    case FlexibleToolBar.GB_LEFT:
                        if (id == 1) Toast.makeText(MainActivity.this, "Menu Add Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case FlexibleToolBar.GB_RIGHT:
                        if (id == 1) Toast.makeText(MainActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        
