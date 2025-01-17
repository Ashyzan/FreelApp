			
			const navBar = document.querySelector('.nav-bar')
		    const navOptionsMobile = document.querySelector('#options-button-mobile')
		    const navOptionsDesktop = document.querySelector('.nav-option-desktop')
			
		      
			
		      function onToggleMenu(e){
				navBar.classList.toggle('top-[-100%]')
		        navBar.classList.toggle('top-14')
		      }

		      function onToggleOptionsMobile(e){
		        navOptionsMobile.classList.toggle('hidden')
		      }

		      function onToggleOptionsDesktop(e){
				navOptionsDesktop.classList.toggle('top-[-100%]')
		        navOptionsDesktop.classList.toggle('top-14')
		      }
			  
			  